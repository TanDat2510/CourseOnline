/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author thang
 */
@Data
public class AddCourseDTO {
    private Integer id;
    private String title;
    private String description;
    private String timeExperted;
    private Long price;
    private String status; // sử dụng String để dễ dàng nhận từ form
    private String courseType; // sử dụng String để dễ dàng nhận từ form
    private Integer categoryId;
    private Integer instructorId;
    private MultipartFile file;
    private String img;
}
