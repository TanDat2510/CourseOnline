/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service.impl;

import com.group8.dto.CategoryDTO;
import com.group8.pojo.Category;
import com.group8.repository.CategoryRepository;
import com.group8.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Implement service to get categories of courses
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository cateRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getCatesDTO() {
        List<Category> categories = this.cateRepo.getCates();
        return categories.stream()
                .map(c -> {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    modelMapper.map(c, categoryDTO);
                    return categoryDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> getCates() {
        return this.cateRepo.getCates();
    }
    
    

}
