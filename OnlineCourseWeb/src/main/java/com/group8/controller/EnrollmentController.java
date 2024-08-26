/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.pojo.CourseStatus;
import com.group8.pojo.CourseType;
import com.group8.pojo.Enrollment;
import com.group8.pojo.EnrollmentStatus;
import com.group8.service.EnrollmentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author thang
 */
@Controller
@ControllerAdvice
public class EnrollmentController {

    @ModelAttribute
    public void commAttrs(Model model) {
        model.addAttribute("enrollmentStatusList", EnrollmentStatus.values());
    }

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private Environment env;

    @RequestMapping("/enrollments")
    public String creatEnrollmentsView(Model model, @RequestParam Map<String, String> params) {
        if (params.get("page") == null) {
            params.put("page", "1");
        }
        List<Enrollment> enrollments = this.enrollmentService.getEnrollments(params);

        int total = this.enrollmentService.getEnrollments(null).size();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!entry.getKey().equals("page") && entry.getValue() != null && !entry.getValue().isEmpty()) {
                params.remove("page");
                total = this.enrollmentService.getEnrollments(params).size();
                break;
            }
        }
        int PAGE_MAX = Integer.parseInt(env.getProperty("page.size.enrollment"));

        int pageTotal = (int) Math.ceil((double) total / PAGE_MAX);

        model.addAttribute("enrollments", enrollments);
        model.addAttribute("pageTotal", pageTotal);
        return "enrollment";
    }
}
