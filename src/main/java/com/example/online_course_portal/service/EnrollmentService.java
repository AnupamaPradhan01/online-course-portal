package com.example.online_course_portal.service;

import com.example.online_course_portal.DAO.CourseRepository;
import com.example.online_course_portal.DAO.EnrollmentRepository;
import com.example.online_course_portal.DAO.UserRepository;
import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.Entity.Enrollment;
import com.example.online_course_portal.Entity.User;
import com.example.online_course_portal.dto.EnrollmentCreationDTO;
import com.example.online_course_portal.dto.EnrollmentResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,UserRepository userRepository,CourseRepository courseRepository){
        this.enrollmentRepository=enrollmentRepository;
        this.userRepository=userRepository;
        this.courseRepository=courseRepository;
    }
    /**
     * Helper method to convert an enrollment entity to an EnrollmentResponseDTO.
     * This is crucial for preventing the LazyInitializationException.
     * @param enrollment The enrollment entity to convert.
     * @return The corresponding EnrollmentResponseDTO.
     */
    private EnrollmentResponseDTO convertToDto(Enrollment enrollment){
        //Ensure that user and course ar not null. if they are lazy-loaded and not accessed.
        //calling .getId() or .getName() will trigger the fetch.
        //This is safe because these methods are called only when you explicitly covert to DTO.
        return new EnrollmentResponseDTO(
                 enrollment.getId(),
                 enrollment.getUser().getId(),
                 enrollment.getUser().getName(),
                 enrollment.getCourse().getId(),
                 enrollment.getCourse().getTitle(),
                 enrollment.getEnrollmentDate()
        );
    }
    /**
     * Creates a new Enrollment for a user in a specific course
     *
     * @param enrollmentDto The DTO containing user ID and course ID.
     * @return The created Enrollment entity.
     * @throws EntityNotFoundException  if the user or course does not exist.
     * @throws IllegalArgumentException if the user is already enrolled in the course.
     */
    @Transactional
    public EnrollmentResponseDTO createEnrollment(EnrollmentCreationDTO enrollmentDto){
        //1.Find User
        User user=userRepository.findById(enrollmentDto.getUserId())
                .orElseThrow(()->new EntityNotFoundException("User not found with ID: " + enrollmentDto.getUserId()));

        if(!user.hasRole("ROLE_STUDENT")){
            throw new IllegalArgumentException("Only students can enroll in courses.");
        }
        //2.Find Course
        Course course=courseRepository.findById(enrollmentDto.getCourseId())
                .orElseThrow(()->new EntityNotFoundException("Course not found with ID: " +enrollmentDto.getCourseId()));

        //3.Check for existing enrollment to prevent duplicates
        if(enrollmentRepository.existsByUserIdAndCourseId(user.getId(),course.getId())){
            throw new IllegalArgumentException("User with ID " + user.getId() + "is already enrolled in Course with ID " + course.getId());
        }
        //4.Create and save the Enrollment entity
        Enrollment enrollment=new Enrollment(user,course, LocalDateTime.now());

        //We wrap the save in a try-catch for DataIntegrityViolationException
        //though the existsBy.... check should usually prevent it
        try{
            Enrollment savedEnrollment=enrollmentRepository.save(enrollment);
            return convertToDto(savedEnrollment);
        }
        catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Failed to create enrollment due to integrity issue. Possible duplicate enrollment.",e);
        }
    }

    /**
     * Retrieves an enrollment by its ID.
     * @param id The ID of the enrollment.
     * @return An Optional containing the enrollment if found, or empty.
     */
    public Optional<EnrollmentResponseDTO> getEnrollmentById(Long id){
        return enrollmentRepository.findById(id)
                .map(this::convertToDto);//convert entity to DTO
    }
    
    /**
     * Retrieves all enrollment for a specific user.
     * @param userId the ID of the user
     * @return A list of enrollments.
     * @throws EntityNotFoundException if the use does not exist.
     */
    public List<EnrollmentResponseDTO> getEnrollmentsByUserId(Long userId){
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        return enrollmentRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());//convert entity to DTO
    }
    /**
     * Retrieves all enrollments for a specific course
     * @param courseId The ID of the course.
     * @return A list of Enrollments.
     * @throws EntityNotFoundException if the course does not exist.
     */
    public List<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId){
        if(!courseRepository.existsById(courseId)){
            throw new EntityNotFoundException("Course not found with ID :" + courseId);
        }
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    /**
     * Retrieves all enrollments in the system
     * @return A list of all enrollments
     */
    public List<EnrollmentResponseDTO> getAllEnrollments(){
        return enrollmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes an enrollment by its ID.
     * @param id The ID of the enrollment to delete.
     * @return true if the enrollment was found and deleted, false otherwise.
     */
    @Transactional
    public boolean deleteEnrollment(Long id){
        if(enrollmentRepository.existsById(id)){
            enrollmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    /**
     * Deletes a specific enrollment by user and course ID.
     * Useful for un-enrolling a student from a course.
     * @param userId The ID of a user.
     * @param courseId The ID of the course.
     * @return true if the enrollment was found and deleted, false otherwise.
     */
    @Transactional
    public boolean deleteEnrollmentByUserIdAndCourseId(Long userId, Long courseId){
        Optional<Enrollment> enrollmentOptional=enrollmentRepository.findByUserIdAndCourseId(userId,courseId);
        if(enrollmentOptional.isPresent()){
            enrollmentRepository.delete(enrollmentOptional.get());
            return true;
        }
        return false;
    }


}
