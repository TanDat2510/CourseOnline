/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.repository.StatsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author TAN DAT
 */
@Controller
public class StatsController {
    
    @Autowired
    private StatsRepository statsRepository;
    
    @RequestMapping("/stats")
    public String viewStats(Model model){
        List<Object[]> revenueStats = statsRepository.statsRevenueByMonth();
        model.addAttribute("revenueStats", revenueStats);
        return "stats";
    }
}
