package com.example.online_course_portal.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

        private final AuthenticationManager authenticationManager;
        public LoginController(AuthenticationManager authenticationManager){
            this.authenticationManager=authenticationManager;
        }
        @PostMapping("/login")
        public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest){
            Authentication authenticationRequest= UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.email(),
                    loginRequest.password()
            );
            try {
                Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
                // You can also set the SecurityContext if you're using sessions:
                // SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
                return ResponseEntity.ok().build(); // 200 OK
            } catch (Exception e) {
                return ResponseEntity.status(401).build(); // 401 Unauthorized
            }
        }
        public record LoginRequest(String email,String password){

        }
}
