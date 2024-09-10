/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.dto.InstructorDTO;
import com.group8.service.InstructorService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TAN DAT
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiInstructorController {

    @Autowired
    private InstructorService instructorService;

    // API để lấy danh sách các instructor
    @GetMapping("/instructor")
    public ResponseEntity<List<InstructorDTO>> getInstructors(@RequestParam Map<String, String> params) {
        // Nếu không có tham số "page", bạn có thể lấy tất cả các instructors hoặc xử lý logic phân trang tại đây
        List<InstructorDTO> instructors = instructorService.getAllInstructorsDTO(params); 
        return new ResponseEntity<>(instructors, HttpStatus.OK);
    }
}