/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.group8.service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author thang
 */
public interface StatsSerivce {

    List<Object[]> statsContentOfCourse(int courseId);   
    
    List<Object[]> statsEnrollmentByPeriod(Map<String, String> params);
    
    List<Object[]> statsEnrollmentGroupByCourse(Map<String, String> params);
    
    List<Object[]> stastRevenueByMonth();
}
