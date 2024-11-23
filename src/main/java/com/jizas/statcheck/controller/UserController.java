package com.jizas.statcheck.controller;

import com.jizas.statcheck.dto.LoginRequest;
import com.jizas.statcheck.dto.SignupRequest;
import com.jizas.statcheck.entity.UserEntity;
import com.jizas.statcheck.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest request) {
        logger.info("Received signup request for email: {}", request.getEmail());

        // Validate request
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            logger.error("Email is missing in signup request");
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email is required"));
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            logger.error("Password is missing in signup request");
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Password is required"));
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            logger.error("Name is missing in signup request");
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Name is required"));
        }

        if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
            logger.error("Phone is missing in signup request");
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Phone is required"));
        }

        try {
            // Convert SignupRequest to UserEntity
            UserEntity user = new UserEntity();
            user.setEmail(request.getEmail().trim());
            user.setPassword(request.getPassword());
            user.setPhoneNumber(request.getPhone().trim());
            user.setRole("USER");

            logger.info("Attempting to register user with email: {}", user.getEmail());
            UserEntity registeredUser = userService.registerUser(user);

            logger.info("Successfully registered user with email: {}", registeredUser.getEmail());
            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully",
                    "email", registeredUser.getEmail()
            ));
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("Email already registered")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Email already registered"));
            }
            return ResponseEntity.badRequest()
                    .body(Map.of("error", errorMessage != null ? errorMessage : "An unexpected error occurred"));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.info("Received login request for email: {}", request.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            if (authentication.isAuthenticated()) {
                UserEntity user = userService.findByEmail(request.getEmail());
                return ResponseEntity.ok(Map.of(
                        "message", "Login successful",
                        "email", user.getEmail(),
                        "role", user.getRole()
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            logger.error("Login failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }
}