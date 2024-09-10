/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service.impl;

import com.group8.repository.StatsRepository;
import com.group8.service.StatsSerivce;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author thang
 */
@Service
public class StatsServiceImpl implements StatsSerivce {

    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> statsContentOfCourse(int courseId) {
        return this.statsRepo.statsContentOfCourse(courseId);
    }

    @Override
    public List<Object[]> statsEnrollmentByPeriod(Map<String, String> params) {
        return this.statsRepo.statsEnrollmentByPeriod(params);
    }

    @Override
    public List<Object[]> statsEnrollmentGroupByCourse(Map<String, String> params) {
        return this.statsRepo.statsEnrollmentGroupByCourse(params);
    }

    @Override
    public List<Object[]> stastRevenueByMonth() {
        return this.statsRepo.statsRevenueByMonth();
    }
    
}
