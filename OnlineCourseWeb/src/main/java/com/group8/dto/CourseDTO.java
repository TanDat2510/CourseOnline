package com.group8.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.group8.pojo.Enum.CourseStatus;
import com.group8.pojo.Enum.CourseType;
import java.util.Date;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {
    private Integer id;
    private String title;
    private String description;
    private String timeExperted;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private long price;
    private CourseStatus status;
    private CourseType courseType;
    private CategoryDTO categoryId;
    private InstructorDTO instructorId;
    private String img;
    private MultipartFile file;
    // todo: check user_id and course_id exits ?
    private boolean isRegister;

    
}
