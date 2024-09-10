package com.group8.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.group8.dto.AddCourseDTO;
import com.group8.dto.CourseDTO;
import com.group8.pojo.Course;
import com.group8.pojo.Enum.CourseStatus;
import com.group8.repository.CourseRepository;
import com.group8.repository.EnrollmentRepository;
import com.group8.repository.UserRepository;
import com.group8.service.CourseService;
import java.io.IOException;
import java.security.Principal;
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

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<CourseDTO> getCourseDTO(Map<String, String> params, Principal principal) {
        int userId = 0;
        if(principal != null){
          userId = this.userRepo.getUserByUsername(principal.getName()).getId();
        }
        params.put("userId", String.valueOf(userId));
        List<Course> courses = this.courseRepo.getCourse(params);
        List<CourseDTO> dto = courses.stream()
                .map(c -> {
                    CourseDTO courseDTO = modelMapper.map(c, CourseDTO.class);
                    // Kiểm tra xem người dùng đã đăng ký khóa học này chưa
                    boolean isRegistered = enrollmentRepo.checkEnrollment(c.getId(), Integer.parseInt(params.get("userId")));
                    // Đặt giá trị cho thuộc tính is_register
                    courseDTO.setRegister(isRegistered);
                    return courseDTO;
                })
                .collect(Collectors.toList());
        return dto;
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
        if (course.getId() == null) {
            course.setCreatedDate(new Date());
        } else {
            course.setCreatedDate(this.courseRepo.getCourseById(course.getId()).getCreatedDate());
        }
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

    @Override
    public void deleteCourse(int id) {
        this.courseRepo.deleteCourse(id);
    }

    @Override
    public Course getCourseByID(int id) {
        return this.courseRepo.getCourseById(id);
    }

    @Override
    public List<CourseDTO> getCourseDTOByInstructor(int instructorId) {
        // Lấy danh sách Course từ repository
        List<Course> courses = this.courseRepo.getCourseDTOByInstructorId(instructorId);

        // Kiểm tra nếu danh sách courses rỗng hoặc null
        if (courses == null || courses.isEmpty()) {
            return List.of(); // Trả về danh sách rỗng
        }

        // Sử dụng ModelMapper để chuyển đổi từ Course sang CourseDTO
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }


    public CourseDTO getCourseDTOByID(int id, Principal principal) {
        int userId = 0;
        if(principal != null){
          userId = this.userRepo.getUserByUsername(principal.getName()).getId();
        }
        Course course = this.courseRepo.getCourseById(id);
        CourseDTO  dto = modelMapper.map(course, CourseDTO.class);
        dto.setRegister(this.enrollmentRepo.checkEnrollment(course.getId(), userId));
        return dto;
    }
}
