/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author thang
 */
@Data
public class InstructorEnrollmentDTO {
//    enrollmentDTO.setId(enrollment.getId());
////            enrollmentDTO.setUserId(enrollment.getUserId());
////            enrollmentDTO.setCourseId(enrollment.getCourseId());
//            enrollmentDTO.setFirstName(enrollment.getUserId().getFirstName());
//            enrollmentDTO.setLastName(enrollment.getUserId().getLastName());
//            enrollmentDTO.setProgress(enrollment.getProgress());
//            enrollmentDTO.setCreatedDate(new Date());
//            enrollmentDTO.setUpdatedDate(new Date());
//            enrollmentDTOs.add(enrollmentDTO);

    private int id;
    private UserDTO userId;
    private CourseDTO courseId;
    private Integer progress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;

    public String getFullName() {
        return userId.getLastName() + userId.getFirstName();
    }

}
