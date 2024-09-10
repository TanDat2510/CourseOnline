package com.group8.pojo.Enum;

public enum CourseType {
    ONLINE("Online"),
    ON_OFF("Offline"),
    HYBRID("Hybrid");

    private String name;

    CourseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}