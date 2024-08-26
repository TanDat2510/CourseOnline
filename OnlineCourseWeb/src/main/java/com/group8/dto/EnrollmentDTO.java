/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.group8.pojo.Course;
import com.group8.pojo.EnrollmentStatus;
import com.group8.pojo.Invoice;
import com.group8.pojo.User;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author TAN DAT
 */
@Data
public class EnrollmentDTO {

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the status
     */
    public EnrollmentStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    /**
     * @return the progress
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * @return the certificateUrl
     */
    public String getCertificateUrl() {
        return certificateUrl;
    }

    /**
     * @param certificateUrl the certificateUrl to set
     */
    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the courseId
     */
    public Course getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    /**
     * @return the invoiceId
     */
    public Invoice getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the userId
     */
    public User getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /**
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return the payerName
     */
    public String getPayerName() {
        return payerName;
    }

    /**
     * @param payerName the payerName to set
     */
    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    /**
     * @return the payerPhone
     */
    public String getPayerPhone() {
        return payerPhone;
    }

    /**
     * @param payerPhone the payerPhone to set
     */
    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }

    /**
     * @return the payerEmail
     */
    public String getPayerEmail() {
        return payerEmail;
    }

    /**
     * @param payerEmail the payerEmail to set
     */
    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    /**
     * @return the totalAmount
     */
    public long getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
    private Integer id;
    private EnrollmentStatus status;
    private Integer progress;
    private String grade;
    private String certificateUrl;
    private Date createdDate;
    private Date updatedDate;
    private Course courseId;
    private Invoice invoiceId;
    private User userId;
    
    private String invoiceNumber;
    private String payerName;
    private String payerPhone;
    private String payerEmail;
//    private String username;
//    private String password;
    private long totalAmount;
    
}
