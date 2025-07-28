package com.example.online_course_portal.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.online_course_portal.Entity.Module;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module,Long> {
    List<Module> findByCourseId(Long courseId);
}
