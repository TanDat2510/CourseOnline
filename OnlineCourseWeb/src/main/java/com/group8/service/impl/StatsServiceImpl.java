/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service.impl;

import com.group8.repository.StatsRepository;
import com.group8.service.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TAN DAT
 */
@Service
public class StatsServiceImpl implements StatsService{
    
    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> stastRevenueByMonth() {
        return this.statsRepo.statsRevenueByMonth();
    }
    
}
