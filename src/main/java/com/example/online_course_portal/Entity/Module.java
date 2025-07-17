package com.example.online_course_portal.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;// E.g Introduction to Spring boot

    @Column(columnDefinition = "TEXT")
    private String description; //Optional: brief overview of the module

    @Column(nullable = false)
    private Integer moduleOrder; //To define the sequence of modules within a course

    //Many-to-one relationship with Course
    //A module belongs to one course
    @ManyToOne(fetch=FetchType.LAZY)//Fetch course lazily by default
    @JoinColumn(name = "course_id",nullable = false)//This specifies the Fk column
    @JsonIgnore
    private Course course;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //Optional: One-to-Many relationship with lessons(covered later)
    //@OneToMany(mappedBy="module",cascade=CascadeType.ALL,orphanRemoval=true)
    //private List<Lesson> lessons;

    //No-arg Constructor required by JPA
    public Module(){}

    //Constructor for creating a new module

    public Module(String title, String description, Integer moduleOrder, Course course, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.moduleOrder = moduleOrder;
        this.course = course;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    //Getters and setters

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

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
    //To String

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", moduleOrder=" + moduleOrder +
                ", course=" + course +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
