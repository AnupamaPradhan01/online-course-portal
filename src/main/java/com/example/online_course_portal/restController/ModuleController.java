package com.example.online_course_portal.restController;

import com.example.online_course_portal.DAO.ModuleRepository;
import com.example.online_course_portal.Entity.Course;
import com.example.online_course_portal.Entity.Module;
import com.example.online_course_portal.dto.ModuleCreationDTO;
import com.example.online_course_portal.dto.ModuleUpdateDTO;
import com.example.online_course_portal.service.ModuleService;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/modules") // Base URL for all endpoints in this
public class ModuleController {
    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    /**
     * Creates a new module.
     * Endpoint: POST /api/modules
     * Request Body: ModuleCreationDTO
     *
     * @param moduleCreationDTO The DTO containing the details for the new module.
     * @return ResponseEntity with the created Module and HTTP Status 201 (Created),
     * or HTTP Status 400 (Bad Request) if the associated course is not found.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Module> createModule(@Valid @RequestBody ModuleCreationDTO moduleCreationDTO) {
        try{
            Module newModule=moduleService.createModule(moduleCreationDTO);
            return new ResponseEntity<>(newModule, HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // You will add other methods here later (GET, PUT, DELETE)
    //Get /api/courses/{id}--->Get courses by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')") // Everyone can view a course
    public ResponseEntity<Module> getModuleByID(@PathVariable Long id){
        Optional<Module> module=moduleService.getModuleID(id);
        return module.map(value->new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Get /api/modules-Get All Modules
    @GetMapping
    public ResponseEntity<List<Module>> getAllModules(){
        List<Module> modules=moduleService.getAllModules();
        if(modules.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(modules,HttpStatus.OK);
    }
    //Add the PUT endpoint for updating a module
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Module> updateModule(@PathVariable Long id, @RequestBody ModuleUpdateDTO moduleUpdateDTO){
        try{
            Module updatedModule=moduleService.updateModule(id,moduleUpdateDTO);
            return new ResponseEntity<>(updatedModule,HttpStatus.OK);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Deletes a module by its ID.
     * This version uses local exception Handing (try-catch ) nad checks existence directly.
     * @param id the ID of the module to delete.
     * @return ResponseEntity with HttpStatus.NO_CONTENT
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id){
        try{
            boolean deleted= moduleService.deleteModule(id);
            if(deleted){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(AccessDeniedException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
