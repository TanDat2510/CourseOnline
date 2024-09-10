/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository;

import com.group8.pojo.Process;

/**
 *
 * @author thang
 */
public interface ProcessRepository {
    int checkProcess(int userId, int contentId);
    
    void addUpProcess(Process process);
}
