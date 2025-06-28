package com.example.online_course_portal.service;

import com.example.online_course_portal.dto.UserRegistrationDto;

public interface UserService {
    String registerUser(UserRegistrationDto userDto);
}
