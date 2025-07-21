package com.example.online_course_portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LessonCreationDTO {

    @NotBlank(message = "Lesson title cannot be empty")
    private String title;

    private String content;//Optional, can be null for video-only lessons

    @NotNull(message="Lesson order cannot be null")
    @Positive(message = "Lesson order must be a positive number")
    private Integer lessonOrder;

    private String videoUrl;
    private String fileUrl;

    @NotBlank(message = "Content type cannot be empty")
    private String contentType;

    @NotNull(message = "Is published status cannot be null")
    private Boolean isPublished;//Use Boolean wrapper for DTO to allow null if not provided, or default to false

    @NotNull(message = "Module Id cannot be null for lesson creation")
    private Long moduleId;//TO link the lesson to a specific module

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(Integer lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // *** CHANGE THIS LINE ***
    public Boolean getIsPublished() { // Renamed from getPublished() to isPublished()
        return isPublished;
    }

    public void setIsPublished(Boolean published) {
        isPublished = published;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }


}
