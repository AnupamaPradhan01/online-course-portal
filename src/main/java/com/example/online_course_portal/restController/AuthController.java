package com.example.online_course_portal.restController;

import com.example.online_course_portal.dto.UserRegistrationDto;
import com.example.online_course_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public String register(@RequestBody UserRegistrationDto userDto){
        return userService.registerUser(userDto);
    }
}
