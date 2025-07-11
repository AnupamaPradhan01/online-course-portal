package com.example.online_course_portal.restController;

import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.dto.CourseCreationDTO;
import com.example.online_course_portal.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
