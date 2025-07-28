package com.example.online_course_portal.service;

import com.example.online_course_portal.DAO.CourseRepository;
import com.example.online_course_portal.DAO.LessonRepository;
import com.example.online_course_portal.DAO.ModuleRepository;
import com.example.online_course_portal.DAO.UserRepository;
import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.Entity.Lesson;
import com.example.online_course_portal.Entity.Module;
import com.example.online_course_portal.dto.CourseCreationDTO;
import com.example.online_course_portal.dto.CourseUpdateDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.online_course_portal.Entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,UserRepository userRepository,ModuleRepository moduleRepository,LessonRepository lessonRepository){
        this.courseRepository=courseRepository;
        this.userRepository=userRepository;
        this.moduleRepository=moduleRepository;
        this.lessonRepository=lessonRepository;
    }
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
    /**
     * Deletes a course by its ID
     * Returns true if the course was found and deleted,false otherwise
     * No specific exception is thrown for 'not found'.
     */
    @Transactional
    public boolean deleteCourse(Long courseId){
        if(courseRepository.existsById(courseId)){
            courseRepository.deleteById(courseId);
            return true;//Indicates successful deletion
        }
        return false; // Indicates course not found
    }
    // ----NEW METHODS FOR PROGRESS TRACKING-----
    /**
     * Retrieves all lessons belonging to a specific course.
     * This method is needed by ProgressService to calculate course Progress.
     * @param courseId The ID of the course.
     * @return A list of all lessons in the course.
     * @throws jakarta.persistence.EntityNotFoundException if the course does not exist.
     */
    public List<Lesson> getAllLessonsInCourse(Long courseId){
        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new EntityNotFoundException("Course not found with ID: " + courseId));

        //Get all modules for the course
        //Note: For this ti work, your Course entity must have a @OneToMany mapping to modules.
        //E.g @OneToMany(mappedBy="Course",cascade=CascadeType.All)
        //private List<Module> modules;
        List<Module> modules=moduleRepository.findByCourseId(courseId);
        if(modules.isEmpty()){
            return Collections.emptyList();//No modules,no lessons
        }
        //Collect all lessons from all modules
        return modules.stream()
                .flatMap(module->lessonRepository.findByModuleId(module.getId()).stream())
                .collect(Collectors.toList());

    }
    /**
     * retrieves the title of a specific course.
     * This method is needed by ProgressService to build the CourseProgressDTO.
     * @param courseId The ID of the course.
     * @return THe title of the course.
     * @throws EntityNotFoundException if the course does not exist.
     */
    public String getCourseTitle(Long courseId){
        Course course=courseRepository.findById(courseId)
                .orElseThrow(()->new EntityNotFoundException("Course not found with ID: " + courseId));
        return course.getTitle();
    }

}
