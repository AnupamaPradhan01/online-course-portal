package com.example.online_course_portal.restController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint(Authentication authentication){
        //the authentication object will contain the authenticated user's details
        String username=authentication.getName();
        return ResponseEntity.ok("Hello, " + username);
    }
    @GetMapping("/admin-only")
    //Example for role-based access (you need a user with ROLE_ADMIN)
    //You'd also need to configure .requestMatchers("/api/test/admin--only").hasRole("ADMIN") in SecurityConfig
    public ResponseEntity<String> adminOnlyEndpoint(){
        return ResponseEntity.ok("Welcome, Admin! This is an admin-only area.");
    }
}
