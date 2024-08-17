/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author thang
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/drashboard")
    public String drashboard() {
        return "drashboard";
    }
}
