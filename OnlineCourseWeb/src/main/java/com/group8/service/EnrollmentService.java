/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.group8.service;

import com.group8.dto.CourseEnrollmentDTO;
import com.group8.dto.EnrollmentDTO;
import com.group8.dto.InstructorEnrollmentDTO;
import com.group8.pojo.Enrollment;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thang
 */
public interface EnrollmentService {

    List<Enrollment> getEnrollments(Map<String, String> params);

    void addOrUpEnrollment(Enrollment enrollment);
    
    CourseEnrollmentDTO getEnrollmentByCourseIdAndUserId(int courseId, Principal principal);
    
    List<InstructorEnrollmentDTO> getEnrollmentsByCourseId(int courseId, Principal principal);
}
