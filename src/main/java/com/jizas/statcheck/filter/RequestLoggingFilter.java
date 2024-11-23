package com.jizas.statcheck.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Log the request details
        logger.info("Incoming Request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        logger.info("Content Type: {}", httpRequest.getContentType());

        // Create a wrapper to log the body
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        // Process the request
        chain.doFilter(wrappedRequest, wrappedResponse);

        // Log the response
        byte[] responseBody = wrappedResponse.getContentAsByteArray();
        String responseContent = new String(responseBody, wrappedResponse.getCharacterEncoding());
        logger.info("Response Status: {}", wrappedResponse.getStatus());
        logger.info("Response Body: {}", responseContent);

        // Copy content to the response
        wrappedResponse.copyBodyToResponse();
    }
}
