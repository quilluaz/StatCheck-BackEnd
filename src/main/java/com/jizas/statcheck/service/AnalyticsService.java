package com.jizas.statcheck.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizas.statcheck.entity.AnalyticsEntity;
import com.jizas.statcheck.repository.AnalyticsRepository;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    // Create new analytics record
    public AnalyticsEntity createAnalyticsRecord(Integer roomId, float usageRate, String peakHours) {
        AnalyticsEntity analyticsEntity = new AnalyticsEntity();
        analyticsEntity.setRoomId(roomId); // Set roomId directly
        analyticsEntity.setUsageRate(usageRate);
        analyticsEntity.setPeakHours(peakHours);
        analyticsEntity.setDateGenerated(LocalDateTime.now()); // Automatically set generation time
        return analyticsRepository.save(analyticsEntity);
    }

    // Get all analytics records
    public List<AnalyticsEntity> getAllAnalytics() {
        return analyticsRepository.findAll();
    }

    // Get a specific analytics record by its ID
    public Optional<AnalyticsEntity> getAnalyticsById(int analyticsId) {
        return analyticsRepository.findById(analyticsId);
    }

    // Update an analytics record
    public AnalyticsEntity updateAnalytics(int analyticsId, float usageRate, String peakHours) {
        Optional<AnalyticsEntity> analyticsOptional = analyticsRepository.findById(analyticsId);
        if (analyticsOptional.isPresent()) {
            AnalyticsEntity analyticsEntity = analyticsOptional.get();
            analyticsEntity.setUsageRate(usageRate);
            analyticsEntity.setPeakHours(peakHours);
            analyticsEntity.setDateGenerated(LocalDateTime.now()); // Update generation time on modification
            return analyticsRepository.save(analyticsEntity);
        }
        return null; // Handle not found case
    }

    // Delete an analytics record
    public String deleteAnalytics(int analyticsId) {
        Optional<AnalyticsEntity> analyticsOptional = analyticsRepository.findById(analyticsId);
        if (analyticsOptional.isPresent()) {
            analyticsRepository.deleteById(analyticsId);
            return "Analytics record deleted successfully";
        } else {
            return "Analytics record not found";
        }
    }

    // Find analytics by room ID
    public List<AnalyticsEntity> getAnalyticsByRoomId(Integer roomId) {
        return analyticsRepository.findByRoomId(roomId);
    }
}
