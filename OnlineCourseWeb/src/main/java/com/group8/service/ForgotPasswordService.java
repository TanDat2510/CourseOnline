/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.group8.service;

import com.group8.pojo.ForgotPassword;
import com.group8.pojo.User;

/**
 *
 * @author TAN DAT
 */
public interface ForgotPasswordService {
    
    public void AddForgotPassword(ForgotPassword fp);

    public ForgotPassword findByOtpAndUSer(int otp, User user);
    
    public void deleteOtp(int id);

    public ForgotPassword getForgotPasswordById(int id);
    
}
