package com.example.online_course_portal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EnrollmentCreationDTO {

    @NotNull(message = "User ID cannot be null")
    @Min(value=1,message = "User Id must be positive")
    private Long userId;

    @NotNull(message = "Course ID cannot be null ")
    @Min(value=1,message = "Course ID must be positive")
    private Long courseId;

    //Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
