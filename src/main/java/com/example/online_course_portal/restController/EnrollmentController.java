package com.example.online_course_portal.restController;

import com.example.online_course_portal.Entity.Enrollment;
import com.example.online_course_portal.Entity.User;
import com.example.online_course_portal.dto.EnrollmentCreationDTO;
import com.example.online_course_portal.dto.EnrollmentResponseDTO;
import com.example.online_course_portal.service.EnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollmentService=enrollmentService;
    }
    /**
     * Enrolls a student in a course
     * Accessible by ADMIN(To enroll anyone) or STUDENT (to enroll themselves).
     *
     * @param enrollmentDto The DTO containing userId and courseId.
     * @param authentication The authenticated user's details.
     * @return The created Enrollment.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STUDENT')")
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment( // Changed return type to DTO
                                                                   @Valid @RequestBody EnrollmentCreationDTO enrollmentDto,
                                                                   Authentication authentication
    ) {
        Long authenticatedUserId = ((User) authentication.getPrincipal()).getId();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Security check for students: can only enroll themselves
        if (!isAdmin && !authenticatedUserId.equals(enrollmentDto.getUserId())) {
            throw new AccessDeniedException("Students can only enroll themselves in courses.");
        }

        try {
            // Service method now directly returns DTO
            EnrollmentResponseDTO newEnrollment= enrollmentService.createEnrollment(enrollmentDto);
            return new ResponseEntity<>(newEnrollment, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during enrollment creation: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    /**
     * Retrieves an enrollment by its ID.
     * Accessible by ADMIN,TEACHER,or STUDENT viewing their own enrollment.
     *
     * @param id The ID of the enrollment
     * @param authentication The authenticated user's details.
     * @return The enrollment.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    public ResponseEntity<EnrollmentResponseDTO> getEnrollmentById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        //Fetch the enrollment DTO
        EnrollmentResponseDTO enrollmentDto=enrollmentService.getEnrollmentById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Enrollment not found with ID: " + id));
        //Security check: ADMIN/TEACHER can view any. STUDENT can only view their own.
        if(authentication.getAuthorities().stream().noneMatch(a->a.getAuthority().equals("ROLE_TEACHER"))){
            //If not ADMIN or TEACHER , it must be a student
            Long authenticatedUserId=((User) authentication.getPrincipal()).getId();
            if(!authenticatedUserId.equals(enrollmentDto.getUserId())){
                throw new AccessDeniedException("You are not authorized to view this enrollment.");
            }
        }
        return new ResponseEntity<>(enrollmentDto,HttpStatus.OK);
    }
    /**
     * Retrieves all enrollments for a specific user.
     * Accessible by ADMIN, TEACHER, or a STUDENT viewing their own enrollments.
     *
     * @param userId The ID of the user.
     * @param authentication The authenticated user's details.
     * @return A list of Enrollments.
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByUserId(
            @PathVariable Long userId,
            Authentication authentication
    ) {
        // Security check: If not ADMIN/TEACHER, ensure student is requesting their own ID
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_TEACHER"))) {
            Long authenticatedUserId = ((User) authentication.getPrincipal()).getId();
            if (!authenticatedUserId.equals(userId)) {
                throw new AccessDeniedException("You are not authorized to view other users' enrollments.");
            }
        }

        try {
            List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
            if (enrollments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(enrollments, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }
    /**
     * Retrieves all enrollments for a specific course.
     * Accessible by ADMIN or TEACHER. Students usually don't see all enrollments for a course.
     *
     * @param courseId The ID of the course.
     * @return A list of Enrollments.
     */
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByCourseId(@PathVariable Long courseId) {
        try {
            List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
            if (enrollments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(enrollments, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }
    /**
     * Retrieves all enrollments in the system.
     * Accessible only by ADMIN.
     *
     * @return A list of all Enrollments.
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EnrollmentResponseDTO>> getAllEnrollments() {
        List<EnrollmentResponseDTO> enrollments = enrollmentService.getAllEnrollments();
        if (enrollments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }
    /**
     * Deletes an enrollment by its ID.
     * Accessible by ADMIN (any enrollment) or STUDENT (their own enrollment).
     *
     * @param id The ID of the enrollment to delete.
     * @param authentication The authenticated user's details.
     * @return ResponseEntity with HttpStatus.NO_CONTENT on success, or error status.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public ResponseEntity<Void> deleteEnrollment(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long authenticatedUserId = ((User) authentication.getPrincipal()).getId();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            // If not admin, verify it's the user's own enrollment
            EnrollmentResponseDTO enrollmentDto = enrollmentService.getEnrollmentById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found with ID: " + id));

            if (!authenticatedUserId.equals(enrollmentDto.getUserId())) {
                throw new AccessDeniedException("You are not authorized to delete this enrollment.");
            }
        }

        if (enrollmentService.deleteEnrollment(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found with ID: " + id);
        }
    }
    /**
     * Un-enrolls a student from a specific course using user ID and course ID.
     * Accessible by ADMIN (for any user/course) or STUDENT (for their own enrollment).
     *
     * @param userId The ID of the user (student).
     * @param courseId The ID of the course.
     * @param authentication The authenticated user's details.
     * @return ResponseEntity with HttpStatus.NO_CONTENT on success, or error status.
     */
    @DeleteMapping("/user/{userId}/course/{courseId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public ResponseEntity<Void> unenrollStudentFromCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId,
            Authentication authentication
    ) {
        Long authenticatedUserId = ((User) authentication.getPrincipal()).getId();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Security check: ADMIN can delete any. STUDENT can only delete their own.
        if (!isAdmin && !authenticatedUserId.equals(userId)) {
            throw new AccessDeniedException("You are not authorized to unenroll other users from courses.");
        }

        if (enrollmentService.deleteEnrollmentByUserIdAndCourseId(userId, courseId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // This might be due to user/course not found, or enrollment not existing
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found for user ID: " + userId + " and course ID: " + courseId);
        }
    }
}
