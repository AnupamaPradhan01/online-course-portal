package com.example.online_course_portal.dto;

public class OptionDTO {
    private Long id; // Optional for creation, but present for response
    private String text;
    private Boolean isCorrect; // Included for creation/teacher view, omitted/ignored for student view

    // no-args Constructor
    public OptionDTO(){}

    //Constructor for creating/teacher view

    public OptionDTO(Long id, String text, Boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
    }
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
