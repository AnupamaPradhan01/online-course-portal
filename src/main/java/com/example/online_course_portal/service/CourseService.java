package com.example.online_course_portal.service;

import com.example.online_course_portal.DAO.CourseRepository;
import com.example.online_course_portal.DAO.UserRepository;
import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.dto.CourseCreationDTO;
import com.example.online_course_portal.dto.CourseUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.online_course_portal.Entity.User;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    /**
     * Retrieves a course by its ID.
     * @param courseID the ID of the course to retrieve.
     * @return An optional containing the Course if found, or empty if not.
     */
    public Optional<Course> getCourseID(Long courseID){
        return courseRepository.findById(courseID);
    }
    /**
     * Retrieves all course
     * @return A list of all Courses.
     */
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }
    /**
     * Updates an existing course
     * @param courseId the ID of the course to update.
     * @param courseUpdateDTO the DTO containing the updated course details.
     * @return The updated CourseResponseDTo.
     * @throws IllegalArgumentException if course is not found.
     *
     */
      @Transactional
      public Course updateCourse(Long courseId, CourseUpdateDTO courseUpdateDTO){
          Optional<Course> existingCourseOptional=courseRepository.findById(courseId);

          if(existingCourseOptional.isEmpty()){
              throw new IllegalArgumentException("Course not found with ID: "+ courseId);
          }

          Course existingCourse=existingCourseOptional.get();

          //Update fields if provided in the DTO
          if(courseUpdateDTO.getTitle()!=null){
              existingCourse.setTitle(courseUpdateDTO.getTitle());
          }
          if(courseUpdateDTO.getDescription()!=null){
              existingCourse.setDescription(courseUpdateDTO.getDescription());
          }
          if(courseUpdateDTO.getPrice()!=null){
              existingCourse.setPrice(courseUpdateDTO.getPrice());
          }
          existingCourse.setUpdatedAt(LocalDateTime.now());
          return courseRepository.save(existingCourse);
      }

}
