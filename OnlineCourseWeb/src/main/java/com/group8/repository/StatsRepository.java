
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author thang
 */
public interface StatsRepository {

    List<Object[]> statsRevenueByMonth();

    List<Object[]> statsContentOfCourse(int courseId);

    List<Object[]> statsEnrollmentByPeriod(Map<String, String> params);

    List<Object[]> statsEnrollmentGroupByCourse(Map<String, String> params);
}
