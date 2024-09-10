/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.dto;

import lombok.Data;

/**
 *
 * @author thang
 */
@Data
public class CourseContentDTO {
    private Integer id;
    private String title;
    private String entityId;
    private String entityType;
    private double point;
}
