package com.example.online_course_portal.DAO;

import com.example.online_course_portal.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    //Custom query methods for convenience
    Optional<Enrollment> findByUserIdAndCourseId(Long userId,Long courseId);
    List<Enrollment> findByUserId(Long userId); //Get all enrollments for a specific user
    List<Enrollment> findByCourseId(Long courseId);//Get all enrollments for a specific course
    boolean existsByUserIdAndCourseId(Long userId,Long courseId);
}

