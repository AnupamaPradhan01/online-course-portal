package com.example.online_course_portal.dto;

import jakarta.validation.constraints.Positive;

public class ModuleUpdateDTO {
    private String title;
    private String description;
    @Positive(message = "Module order must be a positive number")
    private Integer moduleOrder;

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
}
