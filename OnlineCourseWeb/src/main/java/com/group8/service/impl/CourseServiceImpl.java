package com.group8.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.group8.dto.AddCourseDTO;
import com.group8.dto.CourseDTO;
import com.group8.pojo.Course;
import com.group8.repository.CourseRepository;
import com.group8.service.CourseService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<CourseDTO> getCourseDTO(Map<String, String> params) {
        List<Course> courses = this.courseRepo.getCourse(params);
        return courses.stream()
                .map(c -> modelMapper.map(c, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addOrUpCourse(Course course) {
        if (!course.getFile().isEmpty()) {
            try {
                Map<String, Object> res = this.cloudinary.uploader().upload(course.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                course.setImg(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(CourseServiceImpl.class.getName()).log(Level.SEVERE, "Upload file thất bại", ex);
                throw new RuntimeException("Không thể upload hình ảnh. Vui lòng thử lại.");
            }
        }
        course.setCreatedDate(new Date());
        course.setUpdatedDate(new Date());
        this.courseRepo.addOrUpCourse(course);
    }

    @Override
    public List<Course> getCourse(Map<String, String> params) {
        return this.courseRepo.getCourse(params);
    }

    @Override
    public AddCourseDTO getCourseById(int id) {

        Course course = this.courseRepo.getCourseById(id);
        AddCourseDTO addCourseDTO = new AddCourseDTO();
        
        addCourseDTO.setId(course.getId());
        addCourseDTO.setTitle(course.getTitle());
        addCourseDTO.setDescription(course.getDescription());
        addCourseDTO.setTimeExperted(course.getTimeExperted());
        addCourseDTO.setPrice(course.getPrice());
        addCourseDTO.setStatus(course.getStatus().name());
        addCourseDTO.setCourseType(course.getCourseType().name());
        addCourseDTO.setCategoryId(course.getCategoryId().getId());
        addCourseDTO.setInstructorId(course.getInstructorId().getId());
        addCourseDTO.setImg(course.getImg());
        return addCourseDTO;
        
    }
}
