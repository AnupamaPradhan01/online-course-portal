package com.example.online_course_portal.dto;

import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

// This DTO is for teacher/admin view, which includes correct answers
public class QuizWithAnswersDTO {
    private Long id;
    private String title;
    private String description;
    private Integer maxAttempts;
    private Double passingScore;
    private Long lessonId;
    private Long createdByUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<QuestionWithAnswersDTO> questions; //Nested DTO for questions

    public QuizWithAnswersDTO(){}

    // Constructor to map from Quiz entity for teacher/admin view


    public QuizWithAnswersDTO(Long id, String title, String description, Integer maxAttempts, Double passingScore, Long lessonId, Long createdByUserId, LocalDateTime createdAt, LocalDateTime updatedAt, List<QuestionWithAnswersDTO> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.maxAttempts = maxAttempts;
        this.passingScore = passingScore;
        this.lessonId = lessonId;
        this.createdByUserId = createdByUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.questions = questions;
    }
    // Inner DTO for question response(for teacher/admins)
    public static class QuestionWithAnswersDTO{
        private Long id;
        private String text;
        private List<OptionDTO> options; // Re-use OptionDTO which has isCorrect field

        public QuestionWithAnswersDTO(){}

        public QuestionWithAnswersDTO(Long id, String text, List<OptionDTO> options) {
            this.id = id;
            this.text = text;
            this.options = options;
        }

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

        public List<OptionDTO> getOptions() {
            return options;
        }

        public void setOptions(List<OptionDTO> options) {
            this.options = options;
        }
    }

    //Getters and Setters for QuizWithAnswersDTO

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Double getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Double passingScore) {
        this.passingScore = passingScore;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<QuestionWithAnswersDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionWithAnswersDTO> questions) {
        this.questions = questions;
    }
}
