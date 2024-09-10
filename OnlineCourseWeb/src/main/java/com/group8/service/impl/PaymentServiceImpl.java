package com.group8.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group8.dto.PaymentDTO;
import com.group8.pojo.Invoice;
import com.group8.repository.CourseRepository;
import com.group8.repository.InvoiceRepository;
import com.group8.service.PaymentService;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final String momoAccessKey = "F8BBA842ECF85";
    private final String momoSecretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
    private final String momoPartnerCode = "MOMO";
    private final String momoRedirectUrl = "/payments/momo/";
    private final String momoIpnUrl = "/payments/ipn-momo/";
    private final String momoRequestType = "captureWallet";
    private final String momoEndpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
    
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InvoiceRepository invoiceRepo;
    
    @Autowired
    private CourseRepository courseRepo;
    
    @Override
    public ResponseEntity<String> pay(PaymentDTO paymentDTO, HttpServletRequest request) throws Exception {
        if (paymentDTO == null) {
            throw new IllegalArgumentException("PaymentDTO must not be null");
        }

        String requestId = UUID.randomUUID().toString();
        String orderId = paymentDTO.getPaymentChannel() + requestId;
        String orderInfo = this.courseRepo.getOrderInfor(paymentDTO.getCourseCode());
        String amount = String.valueOf(paymentDTO.getTotalAmount());
        String url = "http://localhost:8080/OnlineCourseWeb";
        int userId = paymentDTO.getUserId();
        String courseCode = String.valueOf(userId)+","+paymentDTO.getCourseCode();
        System.out.println("Hello check:" + courseCode);
      

        String rawSignature = String.format(
                "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                momoAccessKey, amount, courseCode, url + momoIpnUrl, orderId, orderInfo,
                momoPartnerCode, url + momoRedirectUrl, requestId, momoRequestType
        );

        String signature = generateSignature(rawSignature, momoSecretKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String jsonData = String.format(
                "{ \"partnerCode\": \"%s\", \"partnerName\": \"Momo Payment\", \"storeId\": \"OnlineCourseView\", \"requestId\": \"%s\", \"amount\": \"%s\", \"orderId\": \"%s\", \"orderInfo\": \"%s\", \"redirectUrl\": \"%s\", \"ipnUrl\": \"%s\", \"lang\": \"vi\", \"extraData\": \"%s\", \"requestType\": \"%s\", \"signature\": \"%s\" }",
                momoPartnerCode, requestId, amount, orderId, orderInfo,
                url + momoRedirectUrl, url + momoIpnUrl,
                courseCode, momoRequestType, signature
        );

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(momoEndpoint, HttpMethod.POST, requestEntity, String.class);

        return response;
    }

    @Override
    public String generateSignature(String rawSignature, String secretKey) throws Exception {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);
            byte[] hash = sha256Hmac.doFinal(rawSignature.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

            // Hoặc trả về dạng Base64 (thay đổi hàm Python để phù hợp)
            // return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("Error generating signature", e);
        }
    }

//    public static String getDomainUrl(HttpServletRequest request) throws URISyntaxException {
//        // Xây dựng URL đầy đủ từ HttpServletRequest
//        String fullUrl = request.getRequestURL().toString();
//
//        // Phân tích URL để lấy scheme và host
//        URI uri = new URI(fullUrl);
//
//        // Trả về domain URL
//        return String.format("%s://%s", uri.getScheme(), uri.getHost());
//    }

    @Override
    public ResponseEntity<Map<String, String>> payWithMoMo(HttpServletRequest request, PaymentDTO paymentDTO) throws Exception {
        // Gọi dịch vụ thanh toán qua MoMo và nhận lại ResponseEntity<String>
        ResponseEntity<String> response = paymentService.pay(paymentDTO, request);

        // Kiểm tra nếu phản hồi không thành công
        if (!response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Chuyển đổi chuỗi JSON trả về thành Map sử dụng Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data;
        try {
            data = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Failed to parse response"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Kiểm tra resultCode từ dữ liệu JSON
        if ((int) data.get("resultCode") != 0) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Transaction is not successful"), HttpStatus.BAD_REQUEST);
        }

        Invoice invoice = new Invoice();
        invoice.setCreatedDate(new Date());
        invoice.setUpdatedDate(new Date());
        invoice.setStatus(Boolean.FALSE);
        invoice.setReferenceCode((String) data.get("requestId"));
        invoice.setPayerEmail(paymentDTO.getPayerEmail());
        invoice.setPayerName(paymentDTO.getPayerName());
        invoice.setPayerPhone(paymentDTO.getPhone());
        invoice.setTotal(paymentDTO.getTotalAmount());

        this.invoiceRepo.addUpInvoice(invoice);

        // Chuẩn bị phản hồi trả về với payment_url
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("payment_url", (String) data.get("payUrl"));
        responseBody.put("requestId", (String) data.get("requestId"));
        

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    public static String encodeToBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    // Phương thức để giải mã chuỗi Base64 trở lại chuỗi gốc
    public static String decodeFromBase64(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String(decodedBytes);
    }

}
