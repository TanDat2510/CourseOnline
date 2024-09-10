package com.group8.controller;

import com.group8.config.MyWebSocketHandler;
import com.group8.dto.PaymentDTO;
import com.group8.pojo.Enrollment;
import com.group8.pojo.Enum.EnrollmentStatus;
import com.group8.pojo.Invoice;
import com.group8.pojo.User;
import com.group8.service.CourseService;
import com.group8.service.EnrollmentService;
import com.group8.service.InvoiceService;
import com.group8.service.PaymentService;
import com.group8.service.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/payments")
@CrossOrigin
public class PaymentController {

    private static final int MOMO_SUCCESS_CODE = 0;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;
    
   
    @PostMapping("/pay")
    public ResponseEntity<Map<String, String>> pay(@RequestBody PaymentDTO paymentDTO, HttpServletRequest request) {
        try {
            // Gọi dịch vụ thanh toán và trả về phản hồi
            return paymentService.payWithMoMo(request, paymentDTO);
        } catch (Exception e) {
            // Xử lý lỗi và trả về thông báo lỗi
            Map<String, String> errorResponse = Map.of("error", "Error processing payment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/ipn-momo")
    public ResponseEntity<String> ipnMomo(@RequestBody Map<String, String> requestData) {
        try {
            if (!requestData.containsKey("resultCode") || !Integer.toString(MOMO_SUCCESS_CODE).equals(requestData.get("resultCode"))) {
                return new ResponseEntity<>("Transaction is not success", HttpStatus.BAD_REQUEST);
            }

            Invoice invoice = this.invoiceService.checkrequestId(requestData.get("requestId"));
            System.out.println("CHeck invoice: " + invoice);
            if (invoice != null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Redirected data is invalid", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/momo")
    @Transactional
    public ResponseEntity<String> returnMomo(HttpServletRequest request) {
        try {
            String resultCode = request.getParameter("resultCode");
            String requestId = request.getParameter("requestId");
            String orderInfo = request.getParameter("orderInfo");
            String transId = request.getParameter("transId");
            String extraData = request.getParameter("extraData");

            System.out.println("Check extraData: " + extraData);

            if (resultCode == null || !Integer.toString(MOMO_SUCCESS_CODE).equals(resultCode)) {
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            }
            Invoice invoice = this.invoiceService.checkrequestId(requestId);
            System.out.println("CHeck invoice: " + invoice);
            if (invoice == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found transaction");
            }

            if (invoice.getTransactionId() == null) {
                invoice.setTransactionId(transId);
                invoice.setStatus(Boolean.TRUE);
                this.invoiceService.addUpInvoice(invoice);

                System.out.println("CHeck info: " + orderInfo);
                String[] stringArray = extraData.split(",");
                int userId = Integer.parseInt(stringArray[0]);

                User u = this.userService.getUserByID(userId);
                System.out.println(u);

                // Duyệt qua từng giá trị trong mảng 
                for (int i = 1; i < stringArray.length; i++) {
                    Enrollment enrollment = new Enrollment();

                    enrollment.setUserId(u);
                    enrollment.setCourseId(this.courseService.getCourseByID(Integer.parseInt(stringArray[i])));
                    enrollment.setInvoiceId(invoice);

                    enrollment.setCreatedDate(new Date());
                    enrollment.setUpdatedDate(new Date());
                    enrollment.setStatus(EnrollmentStatus.IN_PROGRESS);
                    enrollment.setProgress(0);
                    this.enrollmentService.addOrUpEnrollment(enrollment);
                }

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Internal server error");
            }
    
            return new ResponseEntity<>("Paid with Momo successfully", HttpStatus.OK);

        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
