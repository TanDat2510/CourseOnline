/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.group8.service;

import com.group8.dto.AddCourseDTO;
import com.group8.dto.CourseDTO;
import com.group8.pojo.Course;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thang
 */
public interface CourseService {
    List<CourseDTO> getCourseDTO(Map<String, String> params, Principal principal);
    List<Course> getCourse(Map<String, String> params);
    void addOrUpCourse(Course course);
    AddCourseDTO getCourseById(int id);
    void deleteCourse(int id);
    Course getCourseByID(int id);
    List<CourseDTO> getCourseDTOByInstructor(int instructorId);
    CourseDTO getCourseDTOByID(int id, Principal principal);
}
