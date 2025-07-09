package com.example.online_course_portal;

import com.example.online_course_portal.DAO.RoleRepository;
import com.example.online_course_portal.DAO.UserRepository;
import com.example.online_course_portal.Entity.Role;
import com.example.online_course_portal.dto.LoginRequestDto;
import com.example.online_course_portal.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper; // For converting objects to JSON
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest // Loads the full Spring Boot application context
@AutoConfigureMockMvc // Configures MockMvc for testing web layer
@ActiveProfiles("test") // Activates the application-test.properties for this test
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Used to send HTTP requests to controllers

    @Autowired
    private ObjectMapper objectMapper; // Helps convert Java objects to JSON strings

    @Autowired
    private UserRepository userRepository; // To set up test data in the DB
    @Autowired
    private RoleRepository roleRepository; // To set up test data in the DB
    @Autowired
    private PasswordEncoder passwordEncoder; // To encode passwords for test users

    // Tokens to be used across tests
    private String userToken;
    private String adminToken;

    @BeforeEach // This method runs before each test
    void setup() throws Exception {
        // Clear database before each test to ensure isolation
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Create Roles if they don't exist (important for the database setup)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        // Register a regular user (for testing non-admin access)
        UserRegistrationDto userDto = new UserRegistrationDto(
                "Test User", "testuser@example.com", "password123", Collections.singleton("ROLE_USER")
        );
        registerUser(userDto);

        // Register an admin user
        UserRegistrationDto adminDto = new UserRegistrationDto(
                "Admin User", "admin@example.com", "adminpassword", new HashSet<>(Set.of("ROLE_ADMIN"))
        );
        registerUser(adminDto);

        // Login regular user to get token
        LoginRequestDto userLogin = new LoginRequestDto("testuser@example.com", "password123");
        userToken = performLoginAndGetToken(userLogin);

        // Login admin user to get token
        LoginRequestDto adminLogin = new LoginRequestDto("admin@example.com", "adminpassword");
        adminToken = performLoginAndGetToken(adminLogin);
    }

    // Helper method to register users via the API
    private void registerUser(UserRegistrationDto userDto) throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    // Helper method to login and extract the JWT token
    private String performLoginAndGetToken(LoginRequestDto loginDto) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        assertNotNull(token);
        assertTrue(token.length() > 50); // Basic check for a valid token string
        return token;
    }

    // --- Test Cases ---

    @Test
    void testUserLoginSuccess() throws Exception {
        // This is primarily tested by performLoginAndGetToken in beforeEach,
        // but can be repeated for explicit clarity or different scenarios
        LoginRequestDto loginDto = new LoginRequestDto("testuser@example.com", "password123");
        performLoginAndGetToken(loginDto); // This method already has assertions
    }

    @Test
    void testUserLoginFailure_invalidCredentials() throws Exception {
        LoginRequestDto loginDto = new LoginRequestDto("testuser@example.com", "wrongpassword");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized()); // Expect 401
    }

    @Test
    void testAccessProtectedEndpoint_withValidUserToken() throws Exception {
        mockMvc.perform(get("/api/test/protected")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("Hello, testuser@example.com")));
    }

    @Test
    void testAccessProtectedEndpoint_withoutToken() throws Exception {
        mockMvc.perform(get("/api/test/protected"))
                .andExpect(status().isUnauthorized()); // Expect 401
    }

    @Test
    void testAccessProtectedEndpoint_withInvalidToken() throws Exception {
        mockMvc.perform(get("/api/test/protected")
                        .header("Authorization", "Bearer invalid.jwt.token"))
                .andExpect(status().isUnauthorized()); // Expect 401
    }

    @Test
    void testAccessAdminEndpoint_withAdminToken() throws Exception {
        mockMvc.perform(get("/api/test/admin-only")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("Welcome, Admin!")));
    }

    @Test
    void testAccessAdminEndpoint_withRegularUserToken() throws Exception {
        mockMvc.perform(get("/api/test/admin-only")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden()); // Expect 403
    }

    @Test
    void testAccessAdminEndpoint_withoutToken() throws Exception {
        mockMvc.perform(get("/api/test/admin-only"))
                .andExpect(status().isUnauthorized()); // Expect 401
    }
}