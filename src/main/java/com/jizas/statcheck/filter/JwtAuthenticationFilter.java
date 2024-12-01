package com.jizas.statcheck.filter;

import com.jizas.statcheck.security.CustomUserDetailsService;
import com.jizas.statcheck.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            logger.debug("Processing request to: " + request.getRequestURI());
            logger.debug("Request method: " + request.getMethod());
            
            // Log all headers
            java.util.Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                logger.debug("Header: " + headerName + " = " + request.getHeader(headerName));
            }

            // Get token from cookie
            Cookie[] cookies = request.getCookies();
            String token = null;
            if (cookies != null) {
                logger.debug("Found " + cookies.length + " cookies");
                for (Cookie cookie : cookies) {
                    logger.debug("Cookie: " + cookie.getName() + " = " + (cookie.getValue() != null ? cookie.getValue().substring(0, Math.min(10, cookie.getValue().length())) + "..." : "null"));
                    if ("accessToken".equals(cookie.getName())) {
                        token = cookie.getValue();
                        logger.debug("Found access token in cookie");
                    }
                }
            } else {
                logger.debug("No cookies found in request");
            }

            if (token != null) {
                logger.debug("Validating token...");
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.extractEmail(token);
                    String role = jwtUtil.extractRole(token);
                    logger.debug("Token valid for user: " + email + " with role: " + role);
                    
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    logger.debug("User authorities: " + userDetails.getAuthorities());
                    
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                    
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("Authentication set in SecurityContext");
                } else {
                    logger.debug("Token validation failed");
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: ", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed: " + e.getMessage());
        }
    }
}
