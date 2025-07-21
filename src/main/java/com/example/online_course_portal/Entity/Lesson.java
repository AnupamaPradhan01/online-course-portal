package com.example.online_course_portal.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;//E.g, "Lesson 1:Project Setup"

    @Column(columnDefinition = "TEXT")
    private String content;//The actual content of the lesson

    @Column(nullable = false)
    private Integer lessonOrder;//To define the sequence of lessons within a module

    private String videoUrl;//Optional: URL to a video hosted externally(e.g., YouTube, Vimeo)
    private String fileUrl; //Optional: URL to downloadable files(e.g.,PDF,code example)

    @Column(nullable = false)
    private String contentType; // E.g., "video", "text", "quiz", "resource" - defines the primary type of lesson

    @Column(nullable = false)
    private boolean isPublished; //To control visibility of the lesson(e.g., true for published, false for draft)

    //Many-to-one relationship with Module
    //A lesson belong to a one module
    @ManyToOne(fetch = FetchType.LAZY)//Fetch module lazily by default
    @JoinColumn(name = "module_id", nullable = false)//This specifies the FK column
    @JsonIgnore //Prevent infinite recursion in JSON serialization
    private Module module;

    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //No-arg Constructor required by JPA
    public Lesson(){}


    //Constructor for creating a new Lesson
    public Lesson(String title, String content, Integer lessonOrder, String videoUrl, String fileUrl, String contentType, boolean isPublished, Module module, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.lessonOrder = lessonOrder;
        this.videoUrl = videoUrl;
        this.fileUrl = fileUrl;
        this.contentType = contentType;
        this.isPublished = isPublished;
        this.module = module;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(Integer lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    //To String

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", lessonOrder=" + lessonOrder +
                ", videoUrl='" + videoUrl + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", contentType='" + contentType + '\'' +
                ", isPublished=" + isPublished +
                ", module=" + module +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
