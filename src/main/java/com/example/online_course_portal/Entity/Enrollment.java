package com.example.online_course_portal.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="enrollments",uniqueConstraints={@UniqueConstraint(columnNames = {"user_id","course_id"},name = "UK_user_Course")}) //Ensures a user can only enrolled in a course once
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Many-to-one Relationship with User(the Student)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //Many-to-one relationship with Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id",nullable = false)
    private Course course; //The course the student is enrolled in

    @Column(name = "enrollment_date", nullable = false,updatable = false)
    private LocalDateTime enrollmentDate;

    //Optional: You could add more fields like:
    //@Column(name="completion_date")
    //private LocalDateTime completionDate;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status",length = 20)
//    private EnrollmentStatus status;//Enum:PENDING, IN_PROGRESS, COMPLETED, DROPPED

    //No-arg constructor required by JPA
    public Enrollment(){}

    //Constructor for creating a new enrollment
    public Enrollment(User user, Course course, LocalDateTime enrollmentDate) {
        this.user = user;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }
    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    //To String
    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", user=" + user +
                ", course=" + course +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
