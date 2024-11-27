package com.jizas.statcheck.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    
    @Value("${cookie.domain}")
    private String domain;
    
    @Value("${cookie.secure}")
    private boolean secure;
    
    @Value("${cookie.http-only}")
    private boolean httpOnly;
    
    @Value("${cookie.same-site}")
    private String sameSite;

    public ResponseCookie createAccessTokenCookie(String token, long maxAge) {
        return ResponseCookie.from("accessToken", token)
                .domain(domain)
                .path("/")
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .maxAge(maxAge)
                .build();
    }

    public ResponseCookie createRefreshTokenCookie(String token, long maxAge) {
        return ResponseCookie.from("refreshToken", token)
                .domain(domain)
                .path("/")
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .maxAge(maxAge)
                .build();
    }

    public ResponseCookie createExpiredCookie(String name) {
        return ResponseCookie.from(name, "")
                .domain(domain)
                .path("/")
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .maxAge(0)
                .build();
    }
} 