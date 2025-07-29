package com.example.online_course_portal.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class QuizSubmissionDTO {
    @NotNull(message = "Quiz ID must be provided for submission")
    private Long quizId;

    @NotNull(message = "Answers for questions must be provided")
    private List<UserAnswerDTO> answers; //List of student's answers

    public QuizSubmissionDTO(){}

    public QuizSubmissionDTO(Long quizId, List<UserAnswerDTO> answers) {
        this.quizId = quizId;
        this.answers = answers;
    }
    // Getters and Setters

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public List<UserAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserAnswerDTO> answers) {
        this.answers = answers;
    }
}
