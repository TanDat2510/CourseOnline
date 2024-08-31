/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service.impl;

import com.group8.pojo.User;
import com.group8.service.EmailService;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author TAN DAT
 */
@Service
@PropertySource("classpath:configs.properties")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    private Integer otpGenerator() {
        Random random = new Random();
        int origin = 100_000; // Giá trị bắt đầu của phạm vi
        int bound = 999_999;  // Giá trị kết thúc của phạm vi
        int randomNumber = origin + random.nextInt(bound - origin);
        return randomNumber;
    }

    @Override
    public void sendSimpleMessage(User u) {
        SimpleMailMessage mes = new SimpleMailMessage();
        int otp = otpGenerator();
        mes.setFrom(environment.getProperty("spring.mail.username"));
        mes.setText("Thông báo từ Online Course Website "
                + "đây là mã OTP của bạn " + otp + " và đừng gửi nó cho ai khác");
        mes.setSubject("OTP cho yêu cầu quên mật khẩu của bạn");

        javaMailSender.send(mes);
    }

}
