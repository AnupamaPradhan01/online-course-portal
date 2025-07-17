package com.example.online_course_portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class ModuleCreationDTO {

    @NotBlank(message = "Module title cannot be empty")
    private String title;

    private String description;

    @NotNull(message="Module order cannot be null")
    @Positive(message="Module Order must be a positive number")
    private Integer moduleOrder;

    @NotNull(message = "Course Id cannot be null for module creation")
    private Long courseId;

    //Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
