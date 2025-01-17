/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.dto.AddUserDTO;
import com.group8.dto.CourseDTO;
import com.group8.pojo.Category;
import com.group8.pojo.Course;
import com.group8.pojo.Enum.CourseStatus;
import com.group8.pojo.Enum.CourseType;
import com.group8.pojo.Instructor;
import com.group8.service.CourseService;
import com.group8.service.InstructorService;
import com.group8.service.InvoiceService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author thang
 */
@Controller
@RequestMapping("/api")
@CrossOrigin
public class ApiCourseController {

    @Autowired
    private CourseService courseService;
    
    @Autowired
    private InstructorService instructorService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> list(@RequestParam Map<String, String> params, Principal principal) {
        params.put("status", CourseStatus.ACTIVE.name());
       
        return new ResponseEntity<>(this.courseService.getCourseDTO(params, principal), HttpStatus.OK);
    }
    
    
    @RequestMapping("/courses/{courseId}")
    public ResponseEntity<CourseDTO> retrive(@PathVariable(value = "courseId") int id, Principal principal){
        return new ResponseEntity<>(this.courseService.getCourseDTOByID(id, principal), HttpStatus.OK);
    }
    

    @PostMapping(path = "/add-up",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addOrUpdateCourse(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("timeExperted") String timeExperted,
            @RequestParam("price") Long price,
            @RequestParam("status") String status,
            @RequestParam("courseType") String courseType,
            @RequestParam("categoryId") Category categoryId,
            @RequestParam("instructorId") Instructor instructorId,
            @RequestParam("file") MultipartFile file) {
        try {
            Course course = new Course();
            course.setTitle(title);
            course.setDescription(description);
            course.setTimeExperted(timeExperted);
            course.setPrice(price);
            course.setStatus(CourseStatus.valueOf(status)); // Chuyển đổi từ String thành enum
            course.setCourseType(CourseType.valueOf(courseType)); // Chuyển đổi từ String thành enum
            course.setCategoryId(categoryId);
            course.setInstructorId(instructorId);
            course.setFile(file);

            courseService.addOrUpCourse(course);

            return ResponseEntity.ok("Course saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving course: " + e.getMessage());
        }
    }

    @DeleteMapping("/courses/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "courseId") int id) {
        this.courseService.deleteCourse(id);
    }
    
    @GetMapping("instructor/{instructorId}/courses")
    public ResponseEntity<List<CourseDTO>> listCourseDTOByInstructor(@PathVariable(value="instructorId") int instructorId) { 
    // Lấy danh sách khóa học của instructor
        List<CourseDTO> courses = courseService.getCourseDTOByInstructor(instructorId);
        return new ResponseEntity<>(this.courseService.getCourseDTOByInstructor(instructorId), HttpStatus.OK);
    }
    


//    @Autowired
//    private InvoiceService invoiceService;
//
//    @GetMapping("/invoice/view-details/{invoiceId}")
//    public ResponseEntity<List<EnrollmentDTO>> detailsViewInvoice(@PathVariable("invoiceId") int id) {
//        List<EnrollmentDTO> enrollmentDTOs = invoiceService.getInvoiceById(id);
//
//        if (enrollmentDTOs == null || enrollmentDTOs.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(enrollmentDTOs, HttpStatus.OK);
//    }
}
