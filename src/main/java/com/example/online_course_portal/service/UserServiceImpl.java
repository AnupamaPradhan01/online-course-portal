package com.example.online_course_portal.service;

import com.example.online_course_portal.DAO.RoleRepository;
import com.example.online_course_portal.DAO.UserRepository;
import com.example.online_course_portal.dto.UserRegistrationDto;
import com.example.online_course_portal.Entity.Role;
import com.example.online_course_portal.Entity.User;
import org.apache.el.parser.AstFalse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(UserRegistrationDto userDto) {
        //STEP 1:CHECK IF USER ALREADY EXISTS
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            return "Email already registered.";
        }
        //STEP 2:FETCH ROLES
        Set<Role> roles=new HashSet<>();
        for(String roleName:userDto.getRoles()){
            Optional<Role> role=roleRepository.findByName(roleName);
            role.ifPresent(roles::add);
        }
        //STEP 3:CREATE AND SAVE USER
        User user=new User(
                userDto.getName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                true,
                roles
                );
        userRepository.save(user);
        return "User registered successfully!";

    }

    @Override // Add @Override since it's likely part of your UserService interface
    public boolean loginUser(String email, String password){
        // 1. Fetch the user by email
        Optional<User> userOptional = userRepository.findByEmail(email);

        // 2. Check if the user exists
        if(userOptional.isEmpty()){
            return false; // User not found
        }

        User user = userOptional.get();

        // 3. Check if the user is enabled (optional, but good practice)
        if (!user.isEnabled()) {
            return false; // User account is disabled
        }

        // 4. Compare the provided password with the stored hashed password
        //    using passwordEncoder.matches()
        return passwordEncoder.matches(password, user.getPassword());
    }


}
