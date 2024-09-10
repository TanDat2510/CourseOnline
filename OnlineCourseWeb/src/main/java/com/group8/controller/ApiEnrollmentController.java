/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.dto.CourseEnrollmentDTO;
import com.group8.dto.EnrollmentDTO;
import com.group8.dto.InstructorEnrollmentDTO;
import com.group8.pojo.Enrollment;
import com.group8.service.EnrollmentService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author thang
 */
@Controller
@RequestMapping("/api")
@CrossOrigin
public class ApiEnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @RequestMapping("courses/{courseId}/enrollments")
    public ResponseEntity<CourseEnrollmentDTO> list(@PathVariable(value = "courseId") int courseId, Principal principal) {
        return new ResponseEntity<>(this.enrollmentService.getEnrollmentByCourseIdAndUserId(courseId, principal), HttpStatus.OK);
    }

    @GetMapping("/enrollments/{courseId}")
    public ResponseEntity<List<InstructorEnrollmentDTO>> getEnrollmentsByCourseId(@PathVariable("courseId") int courseId, Principal principal) {
        List<InstructorEnrollmentDTO> enrollmentDTOs = this.enrollmentService.getEnrollmentsByCourseId(courseId, principal);

//        if (enrollmentDTOs == null || enrollmentDTOs.isEmpty()) {
//            return ResponseE
        return new ResponseEntity<>(enrollmentDTOs,HttpStatus.OK);
    }
}