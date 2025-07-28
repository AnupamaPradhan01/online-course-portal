package com.example.online_course_portal.service;

import com.example.online_course_portal.DAO.LessonRepository;
import com.example.online_course_portal.DAO.ProgressRepository;
import com.example.online_course_portal.DAO.UserRepository;
import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.Entity.Lesson;
import com.example.online_course_portal.Entity.Progress;
import com.example.online_course_portal.Entity.User;
import com.example.online_course_portal.dto.CourseProgressDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {
    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final CourseService courseService;//Assuming you have a CourseService with a getAllLessonsInCourse method

    @Autowired
    public ProgressService(ProgressRepository progressRepository, UserRepository userRepository, LessonRepository lessonRepository, CourseService courseService){
        this.progressRepository=progressRepository;
        this.userRepository=userRepository;
        this.lessonRepository=lessonRepository;
        this.courseService=courseService;
    }
    @Transactional
    public void markLessonAsCompleted(Long userId,Long lessonId){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found with ID: " + userId));
        Lesson lesson=lessonRepository.findById(lessonId)
                .orElseThrow(()->new EntityNotFoundException("Lesson not found with ID: " + lessonId));
        Optional<Progress> existingProgress=progressRepository.findByUserIdAndLessonId(userId,lessonId);
        if(existingProgress.isPresent()){
            Progress progress=existingProgress.get();
            if(!progress.isCompleted()){
                progress.setCompleted(true);
                progress.setCompletionDate(LocalDateTime.now());
                progressRepository.save(progress);
            }
        }
        else{
            Progress newProgress=new Progress(user,lesson);
            newProgress.setCompleted(true);
            newProgress.setCompletionDate(LocalDateTime.now());
            progressRepository.save(newProgress);
        }
    }
    public boolean isLessonCompleted(Long userId,Long lessonId){
        return progressRepository.findByUserIdAndLessonId(userId,lessonId)
                .map(Progress::isCompleted)
                .orElse(false);
    }
    //This is a crucial method for students to see their progress
    public CourseProgressDTO getCourseProgress(Long userId, Long courseId){
        // You'll need to implement a method in CourseService or CourseRepository
        // to get all lessons for a given course.
        List<Lesson> allLessonsInCourse=courseService.getAllLessonsInCourse(courseId);

        if(allLessonsInCourse.isEmpty()){
            return new CourseProgressDTO(courseId,courseService.getCourseTitle(courseId),0,0);
        }
        long totalLessons=allLessonsInCourse.size();
        long completedLessons=allLessonsInCourse.stream()
                .filter(lesson -> isLessonCompleted(userId,lesson.getId()))
                .count();
        return new CourseProgressDTO(
                courseId,
                courseService.getCourseTitle(courseId),
                (int) completedLessons,
                (int) totalLessons
        );
    }
}


