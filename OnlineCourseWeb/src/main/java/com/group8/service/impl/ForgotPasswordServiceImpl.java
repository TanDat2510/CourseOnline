/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service.impl;

import com.group8.pojo.ForgotPassword;
import com.group8.pojo.User;
import com.group8.repository.ForgotPasswordRepository;
import com.group8.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TAN DAT
 */

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
    
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepo;

    @Override
    public void AddForgotPassword(ForgotPassword fp) {
        this.forgotPasswordRepo.AddForgotPassword(fp);
    }

    @Override
    public ForgotPassword findByOtpAndUSer(int otp, User user) {
        return this.forgotPasswordRepo.findByOtpAndUSer(otp, user);
    }

    @Override
    public void deleteOtp(int id) {
        this.forgotPasswordRepo.deleteOtp(id);
    }

    @Override
    public ForgotPassword getForgotPasswordById(int id) {
        return this.forgotPasswordRepo.getForgotPasswordById(id);
    }
    
}
