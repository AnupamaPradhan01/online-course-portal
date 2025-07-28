package com.example.online_course_portal.restController;

import com.example.online_course_portal.Entity.User;
import com.example.online_course_portal.dto.CourseProgressDTO;
import com.example.online_course_portal.service.ProgressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    @Autowired
    public ProgressController(ProgressService progressService){
        this.progressService=progressService;
    }
    /**
     * Marks a specific lesson as completed for the authenticated user.
     * Accessible only by students.
     *
     * @param lessonId The ID of the lesson to mark as completed.
     * @param authentication The authenticated user's details.
     * @return A success response.
     */
    @PostMapping("/lesson/{lessonId}/complete")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<Void> markLessonAsCompleted(@PathVariable Long lessonId, Authentication authentication){
        Long authenticatedUserId=((User) authentication.getPrincipal()).getId();
        try{
            progressService.markLessonAsCompleted(authenticatedUserId,lessonId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    /**
     * Retrieves the completion status of a specific lesson for the authenticated user.
     * Accessible only by students.
     *
     * @param lessonId The ID of the lesson.
     * @param authentication The authenticated user's details.
     * @return A boolean indicating completion status.
     */
    @GetMapping("/lesson/{lessonId}/status")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<Boolean> getLessonCompletionStatus(@PathVariable Long lessonId,Authentication authentication){
        Long authenticatedUserId=((User) authentication.getPrincipal()).getId();
        boolean isCompleted= progressService.isLessonCompleted(authenticatedUserId,lessonId);
        return new ResponseEntity<>(isCompleted,HttpStatus.OK);
    }
    /**
     * Retrieves the progress for a specific course for the authenticated user.
     * Accessible only by students.
     *
     * @param courseId The ID of the course.
     * @param authentication The authenticated user's details.
     * @return The courseProgressDTO.
     */
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<CourseProgressDTO> getCourseProgress(@PathVariable Long courseId,Authentication authentication){
        Long authenticatedUserId=((User) authentication.getPrincipal()).getId();
        try{
            CourseProgressDTO progress=progressService.getCourseProgress(authenticatedUserId,courseId);
            return new ResponseEntity<>(progress,HttpStatus.OK);
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
