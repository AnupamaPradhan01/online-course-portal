package com.example.online_course_portal.DAO;

import com.example.online_course_portal.Entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress,Long> {
    Optional<Progress> findByUserIdAndLessonId(Long userId,Long lessonId);
    List<Progress> findByUserId(Long userId);
}
