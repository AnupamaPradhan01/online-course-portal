package com.example.online_course_portal.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public class QuizAttemptResponseDTO {
    private Long attemptId;
    private Long quizId;
    private String quizTitle;
    private Long userId;
    private String userName; // Assuming user entity has a getName() method
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private Double scorePercentage;
    private Boolean passed;
    private List<AnsweredQuestionDTO> answeredQuestions;

    public QuizAttemptResponseDTO(){}

    //Constructor

    public QuizAttemptResponseDTO(Long attemptId, Long quizId, String quizTitle, Long userId, String userName, LocalDateTime startTime, LocalDateTime endTime, Integer score, Double scorePercentage, Boolean passed, List<AnsweredQuestionDTO> answeredQuestions) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.userId = userId;
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.scorePercentage = scorePercentage;
        this.passed = passed;
        this.answeredQuestions = answeredQuestions;
    }
    // Inner DTO to show a question with user's answer and the correct answer
    public static class AnsweredQuestionDTO{
        private Long questionId;
        private String questionText;
        private Long selectedOptionId;
        private Long selectedOptionText;
        private Long correctOptionText;
        private Boolean isCorrect;

        public AnsweredQuestionDTO(){}

        public AnsweredQuestionDTO(Long questionId, String questionText, Long selectedOptionId, Long selectedOptionText, Long correctOptionText, Boolean isCorrect) {
            this.questionId = questionId;
            this.questionText = questionText;
            this.selectedOptionId = selectedOptionId;
            this.selectedOptionText = selectedOptionText;
            this.correctOptionText = correctOptionText;
            this.isCorrect = isCorrect;
        }

        //Getters and Setters


        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public Long getSelectedOptionId() {
            return selectedOptionId;
        }

        public void setSelectedOptionId(Long selectedOptionId) {
            this.selectedOptionId = selectedOptionId;
        }

        public Long getSelectedOptionText() {
            return selectedOptionText;
        }

        public void setSelectedOptionText(Long selectedOptionText) {
            this.selectedOptionText = selectedOptionText;
        }

        public Long getCorrectOptionText() {
            return correctOptionText;
        }

        public void setCorrectOptionText(Long correctOptionText) {
            this.correctOptionText = correctOptionText;
        }

        public Boolean getCorrect() {
            return isCorrect;
        }

        public void setCorrect(Boolean correct) {
            isCorrect = correct;
        }
    }

    //Getters and Setters For QuizAttemptResponseDTO


    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(Double scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public List<AnsweredQuestionDTO> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<AnsweredQuestionDTO> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }
}
