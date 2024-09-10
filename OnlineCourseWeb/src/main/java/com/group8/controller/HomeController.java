/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.pojo.Enrollment;
import com.group8.pojo.Enum.EnrollmentStatus;
import com.group8.service.CourseService;
import com.group8.service.EnrollmentService;
import com.group8.service.UserService;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author thang
 */
@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    EnrollmentService enrollmentService;

    @RequestMapping("/")
    public String home(Model model) {

        // Todo: Đây là những truy vấn cơ bản chỉ nằm trên một bảng (Sai logic của mô hình MVC)
        Map<String, String> params = new HashMap<>();
        // User
        params.put("role1", "ROLE_STUDENT");
        model.addAttribute("studentNumber", this.userService.countUsers(params));
        params.clear();
        params.put("role2", "ROLE_INSTRUCTOR");
        model.addAttribute("instructorNumber", this.userService.countUsers(params));
        // Courses
        model.addAttribute("courseNumber", this.courseService.getCourse(null).size());
        int enrollmentNumber = this.enrollmentService.getEnrollments(null).size();
        // Enrollment
        model.addAttribute("enrollmentNumber", enrollmentNumber);
        params.put("status", EnrollmentStatus.IN_PROGRESS.name());
        int enrollmentInProgressNumber = this.enrollmentService.getEnrollments(params).size();
        double percentInProgress = ((double) enrollmentInProgressNumber / enrollmentNumber) * 100;
        model.addAttribute("enrollmentInProgressNumber", percentInProgress);
        params.remove("status");
        List<Enrollment> enrollmentCompletedNumber = this.enrollmentService.getEnrollments(null);
        float totalProcess = 0;
        for (Enrollment e : enrollmentCompletedNumber) {
            totalProcess += e.getProgress();
        }
        DecimalFormat df = new DecimalFormat("#.0");
        model.addAttribute("enrollmentCompletedNumber", df.format(totalProcess / enrollmentCompletedNumber.size()));
        // Invoice

        return "home";
    }
}
