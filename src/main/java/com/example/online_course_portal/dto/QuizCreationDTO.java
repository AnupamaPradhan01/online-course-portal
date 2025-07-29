package com.example.online_course_portal.dto;

import com.example.online_course_portal.Entity.Option;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public class QuizCreationDTO {
    @NotBlank(message = "Quiz title cannot be empty")
    private String title;

    private String description;

    @PositiveOrZero(message = "Max attempts must be positive or zero")
    private Integer maxAttempts; //eg 3

    @Positive(message = "Passing score must be positive")
    private Double passingScore; // e.g 70.0 for 70%

    @NotNull(message = "Lesson ID must be provided for the quiz")
    private Long lessonId; // The ID of the lesson this quiz belongs to

    @NotNull(message = "Created By User ID must be provided")
    private Long createdByUserId; // The ID of the teacher/admin creating the quiz

    @NotNull(message = "Quiz must contain questions")
    private List<QuestionCreationDTO> questions; //nested DTO for questions

    //Constructor
    public QuizCreationDTO(){}

    //Inner DTO for question creation
    public static class QuestionCreationDTO {
        @NotBlank(message = "Question text cannot be empty")
        private String text;

        @NotNull(message = "Question must contain options")
        private List<OptionCreationDTO> options;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<OptionCreationDTO> getOptions() {
            return options;
        }

        public void setOptions(List<OptionCreationDTO> options) {
            this.options = options;
        }
    }

        // Inner DTO for option creation
        public static class OptionCreationDTO{
            @NotBlank(message = "Option text cannot be empty")
            private String text;

            private boolean isCorrect; //Important: for creating quiz with correct answers

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public boolean isCorrect() {
                return isCorrect;
            }

            public void setCorrect(boolean correct) {
                isCorrect = correct;
            }
        }
        // Getters and Setters for QuizCreationDTO


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

    public List<QuestionCreationDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionCreationDTO> questions) {
        this.questions = questions;
    }
}
