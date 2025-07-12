package com.example.online_course_portal.dto;

import java.math.BigDecimal;

public class CourseUpdateDTO {
    private String title;
    private String Description;
    private BigDecimal price;
    //We generally don't allow changing the instructor via update DTO unless there's a specific business cas.

    //Getters ans setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
