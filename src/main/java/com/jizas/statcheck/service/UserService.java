package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.UserEntity;
import com.jizas.statcheck.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserEntity registerUser(UserEntity user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not set
        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

    public UserEntity authenticateUser(String email, String password) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public record ProfileUpdateResult(UserEntity user, boolean emailChanged) {}

    public ProfileUpdateResult updateUserProfile(Long userId, UserEntity updatedUser) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if email is being changed
        boolean isEmailChanged = !existingUser.getEmail().equals(updatedUser.getEmail());
        
        if (isEmailChanged) {
            // Clear any existing tokens/sessions for this user
            // This ensures the user must log in again with the new email
            try {
                SecurityContextHolder.clearContext();
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getResponse();
                
                // Clear cookies
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if ("accessToken".equals(cookie.getName()) || 
                            "refreshToken".equals(cookie.getName())) {
                            cookie.setValue("");
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
            } catch (Exception e) {
                logger.warn("Error clearing security context", e);
            }
        }
        
        // Update user fields
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setName(updatedUser.getName());
        existingUser.setSocialMediaFacebook(updatedUser.getSocialMediaFacebook());
        existingUser.setSocialMediaInstagram(updatedUser.getSocialMediaInstagram());
        existingUser.setSocialMediaTwitter(updatedUser.getSocialMediaTwitter());

        UserEntity savedUser = userRepository.save(existingUser);
        
        return new ProfileUpdateResult(savedUser, isEmailChanged);
    }

    public UserEntity updateUser(Long userId, UserEntity updatedUser) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setName(updatedUser.getName());
        existingUser.setRole(updatedUser.getRole().toUpperCase());
        existingUser.setSocialMediaFacebook(updatedUser.getSocialMediaFacebook());
        existingUser.setSocialMediaInstagram(updatedUser.getSocialMediaInstagram());
        existingUser.setSocialMediaTwitter(updatedUser.getSocialMediaTwitter());

        return userRepository.save(existingUser);
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        // Find the user by email
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Encode and set new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        // Check if the user exists
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete the user
        userRepository.delete(user);
    }
}