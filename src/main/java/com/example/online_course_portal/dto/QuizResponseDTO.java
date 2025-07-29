package com.example.online_course_portal.dto;

import java.util.List;

public class QuizResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Integer maxAttempts;
    private Long lessonId;
    private List<QuestionResponseDTO> questions; //nested DTO for questions

    public QuizResponseDTO(){}

    // Constructor to map from Quiz entity for student view

    public QuizResponseDTO(Long id, String title, String description, Integer maxAttempts, Long lessonId, List<QuestionResponseDTO> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.maxAttempts = maxAttempts;
        this.lessonId = lessonId;
        this.questions = questions;
    }
    // Inner DTO for question response (for students)
    public static class QuestionResponseDTO{
        private Long id;
        private String text;
        private List<OptionResponseDTO> options;

        public QuestionResponseDTO(){}

        public QuestionResponseDTO(Long id, String text, List<OptionResponseDTO> options) {
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

        public List<OptionResponseDTO> getOptions() {
            return options;
        }

        public void setOptions(List<OptionResponseDTO> options) {
            this.options = options;
        }
    }

    // Inner DTO for option response (for students - No isCorrect field)
    public static class OptionResponseDTO{
        private Long id;
        private String text;

        public OptionResponseDTO(){}

        public OptionResponseDTO(Long id, String text) {
            this.id = id;
            this.text = text;
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
    }

    //Getters and Setters for QuizResponseDTO

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

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public List<QuestionResponseDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponseDTO> questions) {
        this.questions = questions;
    }
}
