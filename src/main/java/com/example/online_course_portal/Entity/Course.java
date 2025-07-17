package com.example.online_course_portal.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")//For longer descriptions
    private String description;

    @Column(nullable = false)
    private BigDecimal price;//Use BigDecimal for currency

    //Many-to-One relationship with User(the instructor)
    //instructor_id column will be created in the courses table
    @ManyToOne(fetch=FetchType.LAZY)//Fetch instructor lazily by default
    @JoinColumn(name="instructor_id",nullable = false)//This specifies the FK column
    @JsonIgnore
    private User instructor;//The actual User object representing the instructor

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //One-to-Many relationship with Modules
    //A course can have many modules
    @OneToMany(mappedBy = "course",cascade=CascadeType.ALL,orphanRemoval=true,fetch = FetchType.LAZY)
    private List<Module> modules=new ArrayList<>();//Initialize to prevent NullPointerException

    // No-arg Constructor required by JPA
    public Course(){}

    //Constructor for creating a new course
    public Course(String title, String description, BigDecimal price, User instructor, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.instructor = instructor;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


//Getters and Setters

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
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

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    //TO string

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", instructor=" + instructor +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", modules=" + modules +
                '}';
    }
}
