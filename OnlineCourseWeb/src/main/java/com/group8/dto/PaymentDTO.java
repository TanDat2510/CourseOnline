package com.group8.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PaymentDTO {
    private String paymentChannel;
    private String payerName;
    private String payerEmail;
    private String phone;
    private List<CoursePaymentDTO> courses;
    private Integer userId;

    // Tính tổng số tiền
    public long getTotalAmount() {
        return courses.stream()
                .mapToLong(c -> c.getPrice())
                .sum();
    }
    
    // Lấy tên các khóa học
//    public String getNameCourses() {
//        return courses.stream()
//                .map(c -> c.getTitle())
//                .collect(Collectors.joining(", "));
//    }
    
    // Lấy mã các khóa học
    public String getCourseCode() {
        return courses.stream()
                .map(c -> String.valueOf(c.getId()))
                .collect(Collectors.joining(","));
    }
}
