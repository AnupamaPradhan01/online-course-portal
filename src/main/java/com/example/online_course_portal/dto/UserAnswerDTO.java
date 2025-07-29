package com.example.online_course_portal.dto;

import jakarta.validation.constraints.NotNull;

public class UserAnswerDTO {
    @NotNull(message = "Question ID must be provided for the answer")
    private Long questionId;

    private Long selectedOptionId; // Null if question is skipped/unaswered

    public UserAnswerDTO(){}

    public UserAnswerDTO(Long questionId, Long selectedOptionId) {
        this.questionId = questionId;
        this.selectedOptionId = selectedOptionId;
    }
    //Getters and Setters

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(Long selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }
}
