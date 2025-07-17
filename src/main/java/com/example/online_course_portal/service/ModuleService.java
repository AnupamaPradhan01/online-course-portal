package com.example.online_course_portal.service;

import com.example.online_course_portal.DAO.CourseRepository;
import com.example.online_course_portal.DAO.ModuleRepository;
import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.Entity.Module;
import com.example.online_course_portal.dto.ModuleCreationDTO;
import com.example.online_course_portal.dto.ModuleUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository,CourseRepository courseRepository){
        this.moduleRepository=moduleRepository;
        this.courseRepository=courseRepository;
    }
    /**
     * Creates a new module for a given course.
     *
     * @param moduleCreationDTO The DTO containing module details and the course ID.
     * @return The created Module entity.

     */
    @Transactional
    public Module createModule(ModuleCreationDTO moduleCreationDTO) {
        // Using IllegalStateException instead of ResourceNotFoundException
        Course course = courseRepository.findById(moduleCreationDTO.getCourseId())
                .orElseThrow(() -> new IllegalStateException("Course not found with ID: " + moduleCreationDTO.getCourseId()));

        // Create Module entity using the full constructor
        LocalDateTime now = LocalDateTime.now();
        Module module = new Module(
                moduleCreationDTO.getTitle(),
                moduleCreationDTO.getDescription(),
                moduleCreationDTO.getModuleOrder(),
                course, // Associate the fetched Course object
                now,
                now
        );
        return moduleRepository.save(module);
    }
    /**
     * Retrieves a course by its ID.
     * @param moduleID the ID of the course to retrieve.
     * @return An optional containing the Course if found, or empty if not.
     */
    public Optional<Module> getModuleID(Long moduleID){
        return moduleRepository.findById(moduleID);
    }

    /**
     * Retrieves all Modules
     * @return A list of all Module
     */
    public List<Module> getAllModules(){
        return moduleRepository.findAll();
    }
    /**
     * Updates an existing course
     * @param moduleId the Id of the module to update
     * @param moduleUpdateDTO the DTO containing the updated module details
     * @return The updated ModuleResponseDTo
     * @throws IllegalArgumentException if module is not found.
     */
     @Transactional
    public Module updateModule(Long moduleId, ModuleUpdateDTO moduleUpdateDTO) {
         Optional<Module> existingModuleOptional = moduleRepository.findById(moduleId);
         if (existingModuleOptional.isEmpty()) {
             throw new IllegalArgumentException("Module not Found with ID: " + moduleId);
         }
         Module existingModule = existingModuleOptional.get();

         //Update fields if provided in the DTO
         if (moduleUpdateDTO.getTitle() != null) {
             existingModule.setTitle(moduleUpdateDTO.getTitle());
         }
         if (moduleUpdateDTO.getDescription() != null) {
             existingModule.setDescription(moduleUpdateDTO.getDescription());
         }
         if (moduleUpdateDTO.getModuleOrder() != null) {
             existingModule.setModuleOrder(moduleUpdateDTO.getModuleOrder());
         }
         existingModule.setUpdatedAt(LocalDateTime.now());
         return moduleRepository.save(existingModule);
     }
    /**
     * Deletes a module by its ID
     * Returns true if the course was found and deleted, false otherwise
     * No specific exception is thrown for 'not found'.
     */
    @Transactional
    public boolean deleteModule(Long moduleId){
        if(moduleRepository.existsById(moduleId)){
            moduleRepository.deleteById(moduleId);
            return true;
        }
        return false;
    }
}
