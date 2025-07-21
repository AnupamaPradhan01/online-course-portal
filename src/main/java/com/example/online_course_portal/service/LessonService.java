package com.example.online_course_portal.service;

import com.example.online_course_portal.DAO.LessonRepository;
import com.example.online_course_portal.DAO.ModuleRepository;
import com.example.online_course_portal.Entity.Lesson;
import com.example.online_course_portal.dto.LessonCreationDTO;
import com.example.online_course_portal.dto.LessonUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.online_course_portal.Entity.Module;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository,ModuleRepository moduleRepository){
        this.lessonRepository=lessonRepository;
        this.moduleRepository=moduleRepository;
    }

    /**
     * Creates a new lesson for a given Module.
     *
     * @param lessonCreationDTO the DTO containing lesson details and the module ID.
     * @return The created Lesson entity.
     * @throws IllegalArgumentException if the specified module is not found.
     */
    @Transactional
    public Lesson createLesson(LessonCreationDTO lessonCreationDTO){
        //Find the associated Module
        Module module=moduleRepository.findById(lessonCreationDTO.getModuleId())
                .orElseThrow(()->new IllegalStateException("Module not found with Id: " +lessonCreationDTO.getModuleId()));

        //Create Lesson entity using the full constructor
        LocalDateTime now=LocalDateTime.now();
        Lesson lesson=new Lesson(
                lessonCreationDTO.getTitle(),
                lessonCreationDTO.getContent(),
                lessonCreationDTO.getLessonOrder(),
                lessonCreationDTO.getVideoUrl(),
                lessonCreationDTO.getFileUrl(),
                lessonCreationDTO.getContentType(),
                lessonCreationDTO.getIsPublished(),
                module,
                now,
                now
        );
        return lessonRepository.save(lesson);
    }
    /**
     * Retrieves a lesson by its ID.
     * @param id The ID of the lesson to retrieve.
     * @return The found Lesson entity.
     * @throws IllegalStateException if the lesson is not found.
     */
    public Lesson getLessonById(Long id){
        return lessonRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Lesson not found with ID: "+id));
    }
    /**
     * Retrieves all lessons.
     * @return A list of all Lesson entities.
     */
    public List<Lesson> getAllLessons(){
        return lessonRepository.findAll();
    }
    /**
     * Updates an existing lesson.
     * @param id The ID of the lesson to update.
     * @param lessonUpdateDTO The DTO containing the updated lesson details.
     * @return The updated Lesson entity.
     * @throws IllegalStateException if the lesson is not found or the new module ID is not found.
     */
    @Transactional
    public Lesson updateLesson(Long id, LessonUpdateDTO lessonUpdateDTO){
        Lesson existingLesson=lessonRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Lesson not found with ID: "+id));
        //update fields if provided in DTO
        if(lessonUpdateDTO.getTitle()!=null){
            existingLesson.setTitle(lessonUpdateDTO.getTitle());
        }
        if(lessonUpdateDTO.getContent()!=null){
            existingLesson.setContent(lessonUpdateDTO.getContent());
        }
        if(lessonUpdateDTO.getLessonOrder()!=null){
            existingLesson.setLessonOrder(lessonUpdateDTO.getLessonOrder());
        }
        if(lessonUpdateDTO.getVideoUrl()!=null){
            existingLesson.setVideoUrl(lessonUpdateDTO.getVideoUrl());
        }
        if(lessonUpdateDTO.getFileUrl()!=null){
            existingLesson.setFileUrl(lessonUpdateDTO.getFileUrl());
        }
        if(lessonUpdateDTO.getContentType()!=null){
            existingLesson.setContentType(lessonUpdateDTO.getContentType());
        }
        if(lessonUpdateDTO.getIsPublished()!=null){
            existingLesson.setPublished(lessonUpdateDTO.getIsPublished());
        }
        // If moduleId is provided, find and update the associated module
//        if(lessonUpdateDTO.getModuleId()!=null){
//            Module newModule=moduleRepository.findById(lessonUpdateDTO.getModuleId())
//                    .orElseThrow(()->new IllegalStateException("New Module not found with ID: "+lessonUpdateDTO.getModuleId()));
//            existingLesson.setModule(newModule);
//        }
        existingLesson.setUpdatedAt(LocalDateTime.now());
        return lessonRepository.save(existingLesson);
    }
    /**
     * Deletes a lesson by its ID.
     * @param id The ID of the lesson to delete.
     * @throws IllegalStateException if the lesson is not found.
     */
    @Transactional
    public void deleteLesson(Long id){
        if(!lessonRepository.existsById(id)){
            throw new IllegalStateException("Lesson not found with ID: "+id);
        }
        lessonRepository.deleteById(id);
    }
}
