package com.group8.service;

import com.group8.dto.PaymentDTO;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PaymentService {

    // Phương thức này trả về ResponseEntity<String> thay vì ResponseEntity<Map<String, Object>>
    ResponseEntity<String> pay(PaymentDTO paymentDTO, HttpServletRequest request) throws Exception;

    // Phương thức để tạo chữ ký số từ rawSignature và secretKey
    String generateSignature(String rawSignature, String secretKey) throws Exception;

//    // Phương thức để lấy URL của domain từ HttpServletRequest
//    String getDomainUrl(HttpServletRequest request) throws URISyntaxException;


    // Phương thức này sử dụng kết quả từ phương thức pay để trả về Map<String, String>
    ResponseEntity<Map<String, String>> payWithMoMo(HttpServletRequest request, PaymentDTO paymentDTO) throws Exception;

}
