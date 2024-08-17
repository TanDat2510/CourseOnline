/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.group8.service;

import com.group8.dto.CategoryDTO;
import com.group8.pojo.Category;
import java.util.List;

/**
 *
 * create interface to show services of category
 * 
 */
public interface CategoryService {
    List<CategoryDTO> getCatesDTO();
    List<Category> getCates();
}
