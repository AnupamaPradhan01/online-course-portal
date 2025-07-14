package com.example.online_course_portal.restController;

import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.dto.CourseCreationDTO;
import com.example.online_course_portal.dto.CourseUpdateDTO;
import com.example.online_course_portal.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    public ResponseEntity<Course> createCourse(@RequestBody CourseCreationDTO courseDto){
        //In a real application,you'd get the instructorID from the authenticated user's context
        //For demonstration, lets assume it comes in the DTO for now.
        //It's better to extract it from the authenticated user's security context
        //Future Implementation
        try{
            Course newCourse=courseService.createCourse(courseDto);
            return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get /api/courses/{id}--->Get courses by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')") // Everyone can view a course
    public ResponseEntity<Course> getCourseByID(@PathVariable Long id){
        Optional<Course> course=courseService.getCourseID(id);
        return course.map(value->new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Get /api/courses-Get All Courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses=courseService.getAllCourses();
        if (courses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204 No content if list is empty
        }
        return new ResponseEntity<>(courses,HttpStatus.OK);
    }

    // Add the PUT endpoint for updating a course
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")//only admins and teacher can update courses
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO courseUpdateDTO){
        try{
            Course updatedCourse=courseService.updateCourse(id,courseUpdateDTO);
            return new ResponseEntity<>(updatedCourse,HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//course not found
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//other unexpected errors
        }
    }
    /**
     * Deletes a course by itd ID.
     * This version uses local exception handling (try-catch) and checks existence directly.
     * @param id The ID of the course to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT(204) on success, or 404/403/500 error.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        try{
            boolean deleted= courseService.deleteCourse(id);
            if(deleted){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No content for successful deletion
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404 Not found if course didn't exist
            }
        }
        catch(AccessDeniedException e){
            //Spring security AccessDeniedException for @PreAuthorize
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);//403 forbidden
        } catch (Exception e) {
            //catch-all for any other unexpected error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//500 Internal Server error
        }
    }
}
