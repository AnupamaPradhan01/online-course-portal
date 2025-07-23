package com.example.online_course_portal.dto;

import java.time.LocalDateTime;

public class EnrollmentResponseDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long courseId;
    private String courseTitle;
    private LocalDateTime enrollmentDate;

    public EnrollmentResponseDTO(Long id, Long userId, String username, Long courseId, String courseTitle, LocalDateTime enrollmentDate) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.enrollmentDate = enrollmentDate;
    }
    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
