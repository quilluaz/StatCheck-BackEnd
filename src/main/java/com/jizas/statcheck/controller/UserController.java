package com.jizas.statcheck.controller;

import com.jizas.statcheck.dto.LoginRequest;
import com.jizas.statcheck.dto.SignupRequest;
import com.jizas.statcheck.dto.PasswordChangeRequest;
import com.jizas.statcheck.entity.UserEntity;
import com.jizas.statcheck.service.UserService;
import com.jizas.statcheck.util.JwtUtil;
import com.jizas.statcheck.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            if (authentication.isAuthenticated()) {
                UserEntity user = userService.findByEmail(request.getEmail());
                
                // Generate tokens
                String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
                String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

                // Create cookies with more permissive settings for development
                ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)  // Set to false for development
                    .path("/")
                    .maxAge(jwtUtil.getExpirationTime() / 1000) // Convert to seconds
                    .sameSite("Lax")
                    .build();

                ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(false)  // Set to false for development
                    .path("/")
                    .maxAge(jwtUtil.getRefreshExpirationTime() / 1000) // Convert to seconds
                    .sameSite("Lax")
                    .build();

                // Add debug logging
                logger.debug("Setting cookies in login response");
                logger.debug("Access Token: " + accessToken.substring(0, 20) + "...");
                logger.debug("Access Token Cookie: " + accessTokenCookie.toString());

                return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(Map.of(
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
                    .body(Map.of("error", "Authentication failed"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            UserEntity user = new UserEntity();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setPhoneNumber(request.getPhone());
            user.setName(request.getName());
            user.setRole("USER");

            UserEntity registeredUser = userService.registerUser(user);

            return ResponseEntity.ok(Map.of(
                    "message", "Signup successful",
                    "email", registeredUser.getEmail()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user-profiles/current")
    public ResponseEntity<UserEntity> getCurrentUserProfile(Authentication authentication) {
        String email = authentication.getName();
        UserEntity user = userService.findByEmail(email);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Remove sensitive information
        user.setPassword(null);
        
        return ResponseEntity.ok(user);
    }

    @GetMapping("/verify-token")
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            String accessToken = null;
            
            // Debug logging
            logger.debug("Cookies received: " + (cookies != null ? cookies.length : "null"));
            
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    logger.debug("Cookie found: " + cookie.getName() + "=" + cookie.getValue());
                    if ("accessToken".equals(cookie.getName())) {
                        accessToken = cookie.getValue();
                        break;
                    }
                }
            }

            // Debug logging
            logger.debug("Access token found: " + (accessToken != null));

            if (accessToken != null && jwtUtil.validateToken(accessToken)) {
                String email = jwtUtil.extractEmail(accessToken);
                UserEntity user = userService.findByEmail(email);
                return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "email", email,
                    "role", user.getRole()
                ));
            }

            // If no valid token is found, check for refresh token
            String refreshToken = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refreshToken".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }

            if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
                String email = jwtUtil.extractEmail(refreshToken);
                UserEntity user = userService.findByEmail(email);
                
                // Generate new access token
                String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
                
                // Create new cookie
                ResponseCookie accessTokenCookie = cookieUtil.createAccessTokenCookie(
                    newAccessToken, jwtUtil.getExpirationTime());
                
                // Add cookie to response
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getResponse();
                response.addHeader("Set-Cookie", accessTokenCookie.toString());

                return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "email", email,
                    "role", user.getRole()
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("valid", false));
        } catch (Exception e) {
            logger.error("Token verification failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("valid", false, "error", e.getMessage()));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Refresh token not found"));
        }

        try {
            if (jwtUtil.validateToken(refreshToken)) {
                String email = jwtUtil.extractEmail(refreshToken);
                UserEntity user = userService.findByEmail(email);
                
                // Generate new access token
                String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
                
                // Create new access token cookie
                ResponseCookie accessTokenCookie = cookieUtil.createAccessTokenCookie(
                    newAccessToken, jwtUtil.getExpirationTime());
                
                // Add cookie to response
                response.addHeader("Set-Cookie", accessTokenCookie.toString());

                return ResponseEntity.ok(Map.of("message", "Token refreshed successfully"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid refresh token"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Token refresh failed"));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody PasswordChangeRequest request,
            Authentication authentication
    ) {
        try {
            // Validate password change request
            if (!request.isValidPasswordChange()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid password change request"));
            }

            // Get current user's email from authentication
            String email = authentication.getName();

            // Attempt to change password
            userService.changePassword(
                    email,
                    request.getOldPassword(),
                    request.getNewPassword()
            );

            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/user-profiles/update/{userId}")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserEntity updatedUser,
            Authentication authentication
    ) {
        try {
            String email = authentication.getName();

            UserEntity currentUser = userService.findByEmail(email);

            if (!currentUser.getUserID().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "You can only update your own profile"));
            }

            // Explicitly set the role to the current user's role to prevent it from becoming null
            updatedUser.setRole(currentUser.getRole());

            UserEntity updatedProfile = userService.updateUser(userId, updatedUser);

            // Remove sensitive information before returning
            updatedProfile.setPassword(null);

            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Invalidate the refresh token by clearing the cookie
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
