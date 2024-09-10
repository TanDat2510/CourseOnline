package com.group8.pojo.Enum;

public enum EnrollmentStatus {
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String displayName;

    EnrollmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
