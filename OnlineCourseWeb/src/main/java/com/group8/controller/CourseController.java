/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.dto.AddCourseDTO;
import com.group8.pojo.Category;
import com.group8.pojo.Course;
import com.group8.pojo.CourseStatus;
import com.group8.pojo.CourseType;
import com.group8.pojo.EnrollmentStatus;
import com.group8.pojo.Instructor;
import com.group8.service.CategoryService;
import com.group8.service.CourseService;
import com.group8.service.InstructorService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author thang
 */
@Controller
@ControllerAdvice
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private Environment env;

    @ModelAttribute
    public void commAttrs(Model model) {
        model.addAttribute("categories", categoryService.getCates());
        model.addAttribute("instructors", instructorService.getAllInstructors(null));
        model.addAttribute("courseStatusList", CourseStatus.values());
        model.addAttribute("courseTypesList", CourseType.values());
        model.addAttribute("total", this.courseService.getCourse(null));
        model.addAttribute("enrolllmentStatusList", EnrollmentStatus.values());
    }

    @RequestMapping("/courses")
    public String courseView(Model model, @RequestParam Map<String, String> params) {
        if (params.get("page") == null) {
            params.put("page", "1");
        }
        List<Course> courses = this.courseService.getCourse(params);

        int total = this.courseService.getCourse(null).size();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!entry.getKey().equals("page") && entry.getValue() != null && !entry.getValue().isEmpty()) {
                params.remove("page");
                total = this.courseService.getCourse(params).size();
                break;
            }
        }
        int PAGE_MAX = Integer.parseInt(env.getProperty("page.size.course"));

        int pageTotal = (int) Math.ceil((double) total / PAGE_MAX);

        model.addAttribute("courses", courses);
        model.addAttribute("pageTotal", pageTotal); // gửi biến tổng trang để phân trang
        return "course";
    }

    /**
     *
     * Hàm để gửi đối tượng addCourseDTO lên trang thêm khóa học
     *
     * @param model
     * @return
     */
    @GetMapping("/courses/add-up")
    public String createView(Model model) {
        model.addAttribute("addCourseDTO", new AddCourseDTO());
        return "add-up-course";
    }

    @PostMapping("/courses/add-up")
    public String courseAddOrUpdateView(@ModelAttribute(value = "addCourseDTO") @Valid AddCourseDTO addCourseDTO,
            BindingResult rs,
            RedirectAttributes redirectAttributes) {
        System.out.println("Hello" + addCourseDTO);
        if (rs.hasErrors()) {
            return "add-up-course";
        }
        // Mapping thủ công
        try {
            Course course = new Course();
            course.setId(addCourseDTO.getId());
            course.setTitle(addCourseDTO.getTitle());
            course.setDescription(addCourseDTO.getDescription());
            course.setTimeExperted(addCourseDTO.getTimeExperted());
            course.setPrice(addCourseDTO.getPrice());
            course.setStatus(CourseStatus.valueOf(addCourseDTO.getStatus())); // Chuyển từ String sang Enum
            course.setCourseType(CourseType.valueOf(addCourseDTO.getCourseType())); // Chuyển từ String sang Enum
            course.setCategoryId(new Category(addCourseDTO.getCategoryId())); // Khởi tạo Category từ ID
            course.setInstructorId(new Instructor(addCourseDTO.getInstructorId())); // Khởi tạo Instructor từ ID
            course.setFile(addCourseDTO.getFile());
            course.setImg(addCourseDTO.getImg());

            this.courseService.addOrUpCourse(course);
            redirectAttributes.addFlashAttribute("successMsg", "Khóa học đã được lưu thành công.");
            return "redirect:/courses";  // Chuyển hướng đến danh sách khóa học
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errMsg", ex.getMessage());
            return "redirect:/courses/add-up";  // Chuyển hướng lại trang thêm/sửa khóa học
        }
    }

    @GetMapping("/courses/add-up/{courseId}")
    public String detailsView(Model model, @PathVariable(value = "courseId") int id) {
        model.addAttribute("addCourseDTO", this.courseService.getCourseById(id));
        System.out.println("Hello" + this.courseService.getCourseById(id));

        return "add-up-course";
    }
}
