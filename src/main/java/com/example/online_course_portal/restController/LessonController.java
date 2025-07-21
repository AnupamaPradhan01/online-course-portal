package com.example.online_course_portal.restController;

import com.example.online_course_portal.Entity.Lesson;
import com.example.online_course_portal.dto.LessonCreationDTO;
import com.example.online_course_portal.dto.LessonUpdateDTO;
import com.example.online_course_portal.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService){
        this.lessonService=lessonService;
    }
    /**
     * Creates a new Lesson for a specific module.
     * Endpoint:Post /api/lessons
     * RequestBody: LessonCreationDTO
     * @param lessonCreationDTO The DTO containing the details for the new lesson and its associated module ID.
     * @return ResponseEntity with the created Lesson and HTTP status 201
     * ot Http status 400 if the associated module is not found or validation fails
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Lesson> createLesson(@Valid @RequestBody LessonCreationDTO lessonCreationDTO){
        try{
            Lesson createdLesson=lessonService.createLesson(lessonCreationDTO);
            return new ResponseEntity<>(createdLesson, HttpStatus.CREATED);
        }
        catch(IllegalStateException e){
            System.err.println("Error creating lesson: "+e.getMessage());//log the error
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Retrieves a lesson by its ID.
     * Endpoint: GET /api/lessons/{id}
     * @param id The Id of the lesson to retrieve.
     * @return ResponseEntity with the Lesson and HTTP status 200 OK.
     * or HTTP status 404 if the lesson not exist
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id){
        try{
            Lesson lesson=lessonService.getLessonById(id);
            return new ResponseEntity<>(lesson,HttpStatus.OK);
        }
        catch(IllegalStateException e){
            System.err.println("Error retrieving lesson: "+e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Retrieve all lessons.
     * Endpoint: GET /api/lessons
     * @return ResponseEntity with a list of Lessons and HTTP status 200(OK).
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<Lesson>> getAllLessons(){
        List<Lesson> lessons=lessonService.getAllLessons();
        return new ResponseEntity<>(lessons,HttpStatus.OK);
    }
    /**
     * Updates an existing lesson.
     * Endpoint: PUT /api/lessons/{id}
     * RequestBOdy: LessonUpdateDTO
     * @param id The ID of the lesson to update.
     * @param lessonUpdateDTO The DTO containing the updated lesson details.
     * @return ResponseEntity with the updated Lesson and HTTP status 200 ok
     * or HTTP status 400 for invalid input, 404 if the lesson doesn't exist.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @Valid @RequestBody LessonUpdateDTO lessonUpdateDTO){
        try{
            Lesson updatedLesson=lessonService.updateLesson(id, lessonUpdateDTO);
            return new ResponseEntity<>(updatedLesson,HttpStatus.OK);
        }
        catch (IllegalStateException e){
            System.err.println("Error updating lesson: "+e.getMessage());
            if(e.getMessage().contains("Lesson not found")){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            } else if (e.getMessage().contains("Module not found")) {
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /**
     * Deletes a lesson by its ID>
     * Endpoint: DELETE /api/lessons/{id}
     * @param id The ID of the lesson to delete.
     * @return ResponseEntity with HTTP status 204 on successful
     * or HTTP status 404 if the lesson does not exist.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id){
        try{
            lessonService.deleteLesson(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(IllegalStateException e){
            System.err.println("Error deleting soon: "+ e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
