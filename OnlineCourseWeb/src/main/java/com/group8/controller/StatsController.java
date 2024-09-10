/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.service.StatsSerivce;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author thang
 */
@Controller
@ControllerAdvice
public class StatsController {

    @Autowired
    private StatsSerivce statsService;

    @GetMapping("/stats")
    public String statsEnrollmentByTime(@RequestParam Map<String, String> params, Model model) {
        if (params.get("year") == null || "".equals(params.get("year"))) {
            params.put("year", "2024");

        }
        if (params.get("period") == null) {
            params.put("period", "MONTH");

        }
        List<Object[]> stats = this.statsService.statsEnrollmentByPeriod(params);
        model.addAttribute("enrollmentStats", stats);

        stats = this.statsService.statsEnrollmentGroupByCourse(params);
        model.addAttribute("enrollmentByCourseStats", stats);
        
        List<Object[]> revenueStats = statsService.stastRevenueByMonth();
        model.addAttribute("revenueStats", revenueStats);
        return "chart";

    }
}
