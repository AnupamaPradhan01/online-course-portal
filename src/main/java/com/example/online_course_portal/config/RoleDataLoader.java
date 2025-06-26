package com.example.online_course_portal.config;

import com.example.online_course_portal.DAO.RoleRepository;
import com.example.online_course_portal.Entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleDataLoader {
    @Bean
    public CommandLineRunner loadRoles(RoleRepository roleRepository){
        return args -> {
            if(roleRepository.findByName("ROLE_STUDENT").isEmpty()){
                roleRepository.save(new Role("ROLE_STUDENT"));
            }
            if(roleRepository.findByName("ROLE_TEACHER").isEmpty()){
                roleRepository.save(new Role("ROLE_TEACHER"));
            }
            if(roleRepository.findByName("ROLE_ADMIN").isEmpty()){
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            System.out.println("Default roles added to DB!");
        };
    }
}
