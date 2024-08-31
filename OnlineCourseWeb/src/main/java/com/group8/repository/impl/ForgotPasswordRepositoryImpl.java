/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository.impl;

import com.group8.pojo.ForgotPassword;
import com.group8.pojo.User;
import com.group8.repository.ForgotPasswordRepository;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TAN DAT
 */
@Repository
@Transactional
public class ForgotPasswordRepositoryImpl implements ForgotPasswordRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void AddForgotPassword(ForgotPassword fp) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(fp);
    }

    @Override
    public ForgotPassword findByOtpAndUSer(int otp, User user) {
        Session s = this.factory.getObject().getCurrentSession();

        Query<ForgotPassword> q = s.createQuery("FROM ForgotPassword fp WHERE fp.otp = :otp AND fp.user = :user", ForgotPassword.class);

        q.setParameter("otp", otp);
        q.setParameter("user", user);

        try {
            ForgotPassword result = q.getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteOtp(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ForgotPassword fp = this.getForgotPasswordById(id);
        s.delete(fp);
    }

    @Override
    public ForgotPassword getForgotPasswordById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(ForgotPassword.class, id);
    }

}
