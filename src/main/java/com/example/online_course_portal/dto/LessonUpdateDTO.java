package com.example.online_course_portal.dto;

import jakarta.validation.constraints.Positive;

public class LessonUpdateDTO {
    private String title;
    private String content;
    private String videoUrl;
    private String fileUrl;
    private String contentType;

    @Positive(message = "Lesson order must be positive number")
    private Integer lessonOrder;

    private Boolean isPublished;

    /**
     * Reminder:
     * We generally don't allow changing the moduleID via update DTO
     * unless there's specific business case.
     * If such a feature is needed, it should be handled bya separate dedicated endpoint or service method.
     */
    //Getters and Setters

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

    public Integer getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(Integer lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean published) {
        isPublished = published;
    }
}
