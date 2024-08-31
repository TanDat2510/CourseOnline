/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.group8.service;

import com.group8.pojo.User;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author TAN DAT
 */
public interface EmailService {
    public void sendSimpleMessage(User u);
}
