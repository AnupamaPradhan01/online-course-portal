package com.example.online_course_portal.dto;

public class CourseProgressDTO {
    private Long courseId;
    private String courseTitle;
    private int completedLessons;
    private int totalLessons;
    private double completionPercentage;

    //Constructors,Getters and setters
    public CourseProgressDTO(){}

    public CourseProgressDTO(Long courseId, String courseTitle, int completedLessons, int totalLessons) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.completedLessons = completedLessons;
        this.totalLessons = totalLessons;
        this.completionPercentage = (totalLessons > 0) ? ((double) completedLessons / totalLessons) * 100 : 0;
    }
    // five argument constructor
    public CourseProgressDTO(Long courseId,String courseTitle,int completedLessons,int totalLessons,double completionPercentage){
        this.courseId=courseId;
        this.courseTitle=courseTitle;
        this.completedLessons=completedLessons;
        this.totalLessons=totalLessons;
        this.completionPercentage=completionPercentage;
    }
    //Getters and Setters

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}
