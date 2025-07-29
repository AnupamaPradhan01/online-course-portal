package com.example.online_course_portal.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id",nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL,orphanRemoval = true)
    @OrderBy("id ASC") // Order options by their ID
    private List<Option> options;

    //Constructors
    public Question(){}

    public Question(String text, Quiz quiz) {
        this.text = text;
        this.quiz = quiz;
    }
    //Getters and setters

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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
