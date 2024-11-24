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

import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        logger.info("Fetching all users");

        try {
            List<UserEntity> users = userService.getAllUsers(); // Assuming this method exists in UserService
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            return ResponseEntity.status(500).body(null);  // Return a 500 error if something goes wrong
        }
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        logger.info("Processing logout request");
        try {
            // Clear any server-side session/token if needed
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        } catch (Exception e) {
            logger.error("Logout failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Logout failed"));
        }
    }

    @PutMapping("/{userID}")
    public ResponseEntity<?> updateUser(@PathVariable Long userID, @RequestBody UserEntity updatedUser) {
        logger.info("Attempting to update user with ID: {}", userID);

        try {
            // Use the existing method to find the user by ID
            UserEntity existingUser = userService.findById(userID);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found"));
            }

            // Update only the necessary fields
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setRole(updatedUser.getRole());

            // Call the updateUser method in service to persist the changes
            UserEntity savedUser = userService.updateUser(userID, existingUser);
            logger.info("Successfully updated user with ID: {}", savedUser.getUserID());

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update user. Please try again."));
        }
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        logger.info("Attempting to delete user with ID: {}", userId);

        try {
            // Call service method
            userService.deleteUser(userId);
            logger.info("Successfully deleted user with ID: {}", userId);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (RuntimeException e) {
            logger.error("Error deleting user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}