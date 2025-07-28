package com.example.online_course_portal.DAO;

import com.example.online_course_portal.Entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    List<Lesson> findByModuleId(Long moduleId);
}
