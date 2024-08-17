package com.group8.pojo;

public enum CourseStatus {
    ACTIVE,
    INACTIVE,
    ARCHIVED;
    
    @Override
    public String toString() {
        return name(); // Đảm bảo rằng `name()` trả về tên của enum
    }
}