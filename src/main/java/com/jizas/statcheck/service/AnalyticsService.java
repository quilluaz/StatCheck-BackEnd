package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.AnalyticsEntity;
import com.jizas.statcheck.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    @Autowired
    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public List<AnalyticsEntity> getAllAnalytics() {
        return analyticsRepository.findAll();
    }

    public Optional<AnalyticsEntity> getAnalyticsById(Long id) {
        return analyticsRepository.findById(id);
    }

    public AnalyticsEntity createAnalytics(AnalyticsEntity analytics) {
        validateAnalytics(analytics);
        return analyticsRepository.save(analytics);
    }

    public AnalyticsEntity updateAnalytics(Long id, AnalyticsEntity analytics) {
        Optional<AnalyticsEntity> existingAnalytics = getAnalyticsById(id);
        if (existingAnalytics.isEmpty()) {
            throw new IllegalArgumentException("Analytics not found with id: " + id);
        }

        validateAnalytics(analytics);
        
        AnalyticsEntity updatedAnalytics = existingAnalytics.get();
        updatedAnalytics.setRoom(analytics.getRoom());
        updatedAnalytics.setUsageRate(analytics.getUsageRate());
        updatedAnalytics.setPeakHours(analytics.getPeakHours());
        updatedAnalytics.setDateGenerated(analytics.getDateGenerated());
        
        return analyticsRepository.save(updatedAnalytics);
    }

    public void deleteAnalytics(Long id) {
        if (!analyticsRepository.existsById(id)) {
            throw new IllegalArgumentException("Analytics not found with id: " + id);
        }
        analyticsRepository.deleteById(id);
    }

    public List<AnalyticsEntity> getAnalyticsByRoomId(Long roomId) {
        return analyticsRepository.findByRoomRoomId(roomId);
    }

    private void validateAnalytics(AnalyticsEntity analytics) {
        if (analytics.getRoom() == null) {
            throw new IllegalArgumentException("Room is required");
        }
        if (analytics.getUsageRate() == null || analytics.getUsageRate() < 0 || analytics.getUsageRate() > 100) {
            throw new IllegalArgumentException("Usage rate must be between 0 and 100");
        }
        if (analytics.getDateGenerated() == null) {
            throw new IllegalArgumentException("Date generated is required");
        }
    }
} 