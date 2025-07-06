package com.example.online_course_portal.restController;

import com.example.online_course_portal.dto.LoginRequestDto;
import com.example.online_course_portal.dto.UserRegistrationDto;
import com.example.online_course_portal.security.JwtService;
import com.example.online_course_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;//keep for registration and internal logic

    @Autowired
    private JwtService jwtService;//New: Inject JwtService

    @Autowired
    private AuthenticationManager authenticationManager;//New: Inject AuthenticationManager

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto userDto){
        String result=userService.registerUser(userDto);
        if(result.equals("User registered successfully!")){
            return  new ResponseEntity<>(result,HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(result,HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest) {
        try{
            //Authenticate the user using Spring security's AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            if (authentication.isAuthenticated()){
                //If authentication is successful,generate a new TOKEN
                String token=jwtService.generateToken(loginRequest.getEmail());
                return new ResponseEntity<>(token,HttpStatus.OK);//return the token
            }
            else{
               //This part should be ideally not be reached if authenticationManager.authenticate throws
                return new ResponseEntity<>("Authetication failed:Not authenticated", HttpStatus.UNAUTHORIZED);
            }
        }
        catch(UsernameNotFoundException e){
            //catches if CustomUserDetailsService throws UsernameNotFoundException
            return new ResponseEntity<>("Invalid email or password",HttpStatus.UNAUTHORIZED);

        }
        catch(Exception e){
           return new ResponseEntity<>("Invalid email or password",HttpStatus.UNAUTHORIZED);
        }



        // Change from userService.authenticate to userService.loginUser
//        boolean authenticated = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword()); // <-- CORRECTED!
//
//        if (authenticated) {
//            return new ResponseEntity<>("Login successful", HttpStatus.OK); // 200 OK
//        } else {
//            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED); // 401 Unauthorized
//        }

    }
}
