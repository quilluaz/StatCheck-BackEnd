package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.AnalyticsEntity;
import com.jizas.statcheck.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/analytics")
@PreAuthorize("hasRole('ADMIN')")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Autowired
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public ResponseEntity<List<AnalyticsEntity>> getAllAnalytics() {
        List<AnalyticsEntity> analytics = analyticsService.getAllAnalytics();
        return new ResponseEntity<>(analytics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyticsEntity> getAnalyticsById(@PathVariable Long id) {
        Optional<AnalyticsEntity> analytics = analyticsService.getAnalyticsById(id);
        return analytics.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AnalyticsEntity> createAnalytics(
            @RequestBody AnalyticsEntity analytics,
            Authentication authentication) {
        try {
            analytics.setCreatedBy(authentication.getName());
            AnalyticsEntity savedAnalytics = analyticsService.createAnalytics(analytics);
            return new ResponseEntity<>(savedAnalytics, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalyticsEntity> updateAnalytics(
            @PathVariable Long id,
            @RequestBody AnalyticsEntity analytics) {
        try {
            AnalyticsEntity updatedAnalytics = analyticsService.updateAnalytics(id, analytics);
            return new ResponseEntity<>(updatedAnalytics, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalytics(@PathVariable Long id) {
        try {
            analyticsService.deleteAnalytics(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<AnalyticsEntity>> getAnalyticsByRoomId(@PathVariable Long roomId) {
        List<AnalyticsEntity> analytics = analyticsService.getAnalyticsByRoomId(roomId);
        return new ResponseEntity<>(analytics, HttpStatus.OK);
    }
} 