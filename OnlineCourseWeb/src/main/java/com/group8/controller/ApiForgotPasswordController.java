/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.pojo.ForgotPassword;
import com.group8.pojo.User;
import com.group8.service.ForgotPasswordService;
import com.group8.service.UserService;
import java.time.Instant;
import java.util.Date;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TAN DAT
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")  // Bật CORS cho API này
@PropertySource("classpath:configs.properties")
public class ApiForgotPasswordController {

    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ForgotPasswordService forgotPasswordService;
    @Autowired
    private Environment environment;

    private Integer otpGenerator() {
        Random random = new Random();
        int origin = 100_000; // Giá trị bắt đầu của phạm vi
        int bound = 999_999;  // Giá trị kết thúc của phạm vi
        int randomNumber = origin + random.nextInt(bound - origin);
        return randomNumber;
    }

    @PostMapping(value = "/verifyAccount/{username}")
    public ResponseEntity<String> verifyAccount(@PathVariable String username) {
        User user = this.userService.getUserByUsername(username);

        System.err.println("EMAILFROM: " + environment.getProperty("spring.mail.username"));

        int otp = otpGenerator();
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom(environment.getProperty("spring.mail.username"));
            helper.setTo(user.getEmail());
            
            // Nội dung HTML cho email
            String content = "<h1>Thông báo từ Online Course Web</h1>"
                    + "<p>Xin chào <b>" + user.getUsername() + "</b>,</p>"
                    + "<p>Đây là mã OTP của bạn:</p>"
                    + "<h2 style='color:#ff6600;'>" + otp + "</h2>"
                    + "<p>Vui lòng không chia sẻ mã này với bất kỳ ai.</p>"
                    + "<br><p>Trân trọng,</p>"
                    + "<p>Đội ngũ hỗ trợ Online Course Web</p>";
            helper.setText(content, true);  // Chuyển đổi sang định dạng HTML
            helper.setSubject("OTP cho yêu cầu quên mật khẩu của bạn");

            ForgotPassword fp = new ForgotPassword();
            fp.setOtp(otp);
            fp.setExpirationTime(new Date(System.currentTimeMillis() + 600 * 1000));
            fp.setUser(user);

            this.forgotPasswordService.AddForgotPassword(fp);
            javaMailSender.send(mimeMessage);
            return ResponseEntity.ok("Đã gửi mã OTP thành công tới email của bạn!");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi gửi email! Vui lòng thử lại sau.");
        }
    }

    @PostMapping("/verifyOtp/{otp}/{username}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String username) {
        User user = this.userService.getUserByUsername(username);
        ForgotPassword fp = this.forgotPasswordService.findByOtpAndUSer(otp, user);

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordService.deleteOtp(fp.getId());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP đã xác thực!");
    }

}
