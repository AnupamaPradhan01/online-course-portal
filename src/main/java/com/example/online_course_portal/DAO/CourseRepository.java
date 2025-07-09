package com.example.online_course_portal.DAO;

import com.example.online_course_portal.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
