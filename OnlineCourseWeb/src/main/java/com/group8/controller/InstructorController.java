/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;


import com.group8.dto.AddUserDTO;
import com.group8.pojo.Instructor;
import com.group8.pojo.User;
import com.group8.service.InstructorService;
import com.group8.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author TAN DAT
 */
@Controller
public class InstructorController {

    @Autowired
    private InstructorService instructorService;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private UserService userService;

    @RequestMapping("/instructor")
    public String viewInstructor(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("instructor", this.instructorService.getAllInstructors());
        return "instructor";

    }

    @GetMapping("/instructor/add-up")
    public String createUserInstructor(Model model) {
        model.addAttribute("AddUserDTO", new AddUserDTO());

        return "add-up-instructor";
    }

    @PostMapping("instructor/add-up")
    public String userInstructorAddOrUpdateView(@ModelAttribute(value = "AddUserDTO")
            @Valid AddUserDTO addUserDTO,
         
            BindingResult rs, RedirectAttributes redirectAttributes,
            Map<String, String> params) {
        System.out.println("Hello" + addUserDTO);
        if (rs.hasErrors()) {
            return "add-up-course";
        }
        try {

            User user = new User();
            //user.setId(addUserDTO.getId());
            user.setFirstName(addUserDTO.getFirstName());
            user.setLastName(addUserDTO.getLastName());
            user.setEmail(addUserDTO.getEmail());
            user.setPhone(addUserDTO.getPhone());
            user.setUsername(addUserDTO.getUsername());
            String pass = this.passEncoder.encode(addUserDTO.getPassword());
            user.setPassword(pass);
            user.setUserRole("ROLE_INSTRUCTOR"); // Cố định role user
            user.setFile(addUserDTO.getFile());
            this.userService.addUserInstructor(user);
            redirectAttributes.addFlashAttribute("successMsg", "Giảng viên đã được lưu thành công.");

            Instructor instructor = new Instructor();
            instructor.setUserId(user);
            instructor.setExpertise(addUserDTO.getExpertise());//Tạo thuộc tính giả trong addUserDTO
            instructor.setDescription(addUserDTO.getDescription());
            this.instructorService.addInstructor(instructor);
            

            return "redirect:/instructor";  // Chuyển hướng đến danh sách Giảng Viên
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errMsg", ex.getMessage());
            return "redirect:/instructor/add-up";  // Chuyển hướng lại trang thêm/sửa Giảng Viên
        }
    }
    @GetMapping("/instructor/add-up/{instructorId}")
    public String detailsView(Model model, @PathVariable(value = "instructorId") int id) {
        model.addAttribute("AddUserDTO", this.instructorService.getInstructorById(id));
        System.out.println("Hello" + this.instructorService.getInstructorById(id));

        return "add-up-instructor";
    }
}
