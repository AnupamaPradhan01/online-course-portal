package com.example.online_course_portal.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_answers", uniqueConstraints = @UniqueConstraint(columnNames = {"quiz_attempt_id","question_id"}))
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_attempt_id",nullable = false)
    private QuizAttempt quizAttempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id",nullable = false)
    private Question question;

    // The option chosen by the user. If null, question was unanswered.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id")
    private Option selectedOption;

    @Column(nullable = false)
    private boolean isCorrect; //Whether the selected option was actually correct

    // no-arg Constructors
    public UserAnswer(){}

    public UserAnswer(QuizAttempt quizAttempt, Question question, Option selectedOption, boolean isCorrect) {
        this.quizAttempt = quizAttempt;
        this.question = question;
        this.selectedOption = selectedOption;
        this.isCorrect = isCorrect;
    }
    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizAttempt getQuizAttempt() {
        return quizAttempt;
    }

    public void setQuizAttempt(QuizAttempt quizAttempt) {
        this.quizAttempt = quizAttempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Option selectedOption) {
        this.selectedOption = selectedOption;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
