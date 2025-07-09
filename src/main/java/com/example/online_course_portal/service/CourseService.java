package com.example.online_course_portal.service;

import com.example.online_course_portal.DAO.CourseRepository;
import com.example.online_course_portal.DAO.UserRepository;
import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.dto.CourseCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.online_course_portal.Entity.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public Course createCourse(CourseCreationDTO courseDto){
        Optional<User> instructorOptional=userRepository.findById(courseDto.getInstructorId());

        if(instructorOptional.isEmpty()){
            throw new IllegalArgumentException("Instructor not found with ID:"+courseDto.getInstructorId());
        }
        User instructor=instructorOptional.get();

        //Optional: Add a check to ensure the user actually has the INSTRUCTOR role
        if(!instructor.hasRole("ROLE_TEACHER")){
            throw new IllegalArgumentException("User with ID " + courseDto.getInstructorId() +"is not an instructor.");
        }

        // Create Course entity using the full constructor
        LocalDateTime now = LocalDateTime.now();
        Course course = new Course(
                courseDto.getTitle(),
                courseDto.getDescription(),
                courseDto.getPrice(),
                instructor,
                now,  // createdAt
                now   // updatedAt
        );
        return courseRepository.save(course);
    }
    // other course related methods(get,update,delete)
}
