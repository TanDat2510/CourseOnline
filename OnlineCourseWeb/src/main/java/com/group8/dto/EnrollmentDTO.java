/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.dto;

import com.group8.pojo.Course;
import com.group8.pojo.Enum.EnrollmentStatus;
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
    
    private String referenceCode;
    private String payerName;
    private String payerPhone;
    private String payerEmail;
//    private String username;
//    private String password;
    
    private String firstName;
    private String lastName;
    private String title;
    private long price;
    
    
}
