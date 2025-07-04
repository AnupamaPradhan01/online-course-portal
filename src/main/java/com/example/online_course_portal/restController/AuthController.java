package com.example.online_course_portal.restController;

import com.example.online_course_portal.dto.LoginRequestDto;
import com.example.online_course_portal.dto.UserRegistrationDto;
import com.example.online_course_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public String register(@RequestBody UserRegistrationDto userDto){
        return userService.registerUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest) {
        System.out.println(loginRequest.getEmail());
        //System.out.println(password);
        // Change from userService.authenticate to userService.loginUser
        boolean authenticated = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword()); // <-- CORRECTED!

        if (authenticated) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }

    }
}
