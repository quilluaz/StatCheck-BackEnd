package com.jizas.statcheck.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jizas.statcheck.entity.AnalyticsEntity;
import com.jizas.statcheck.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/print")
    public String print() {
        return "Hello, Analytics!";
    }

@PostMapping("/create")
public ResponseEntity<AnalyticsEntity> createAnalytics(@RequestBody AnalyticsEntity analyticsEntity) {
    // Validate usage rate
    if (analyticsEntity.getUsageRate() < 0 || analyticsEntity.getUsageRate() > 100) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    // Create the analytics record using the service
    AnalyticsEntity createdAnalytics = analyticsService.createAnalyticsRecord(
            analyticsEntity.getRoomId(),
            analyticsEntity.getUsageRate(),
            analyticsEntity.getPeakHours()
    );
    
    return new ResponseEntity<>(createdAnalytics, HttpStatus.CREATED);
}

    @GetMapping("/getAll")
    public ResponseEntity<List<AnalyticsEntity>> getAllAnalytics() {
        List<AnalyticsEntity> analyticsList = analyticsService.getAllAnalytics();
        return new ResponseEntity<>(analyticsList, HttpStatus.OK);
    }

    @GetMapping("/getById/{analyticsId}")
    public ResponseEntity<AnalyticsEntity> getAnalyticsById(@PathVariable int analyticsId) {
        try {
            AnalyticsEntity analytics = analyticsService.getAnalyticsById(analyticsId).orElseThrow(NoSuchElementException::new);
            return new ResponseEntity<>(analytics, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{analyticsId}")
    public ResponseEntity<AnalyticsEntity> updateAnalytics(
            @PathVariable int analyticsId,
            @RequestBody AnalyticsEntity analyticsEntity) {
        
        // Validate usage rate
        if (analyticsEntity.getUsageRate() < 0 || analyticsEntity.getUsageRate() > 100) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        // Perform the update operation
        AnalyticsEntity updatedAnalytics = analyticsService.updateAnalytics(
                analyticsId,
                analyticsEntity.getUsageRate(),
                analyticsEntity.getPeakHours()
        );
        return updatedAnalytics != null ?
                new ResponseEntity<>(updatedAnalytics, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    

    @DeleteMapping("/delete/{analyticsId}")
    public ResponseEntity<String> deleteAnalytics(@PathVariable int analyticsId) {
        String responseMessage = analyticsService.deleteAnalytics(analyticsId);
        return responseMessage.equals("Analytics record deleted successfully") ?
                new ResponseEntity<>(responseMessage, HttpStatus.OK) :
                new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByRoomId/{roomId}")
    public ResponseEntity<List<AnalyticsEntity>> getAnalyticsByRoomId(@PathVariable int roomId) {
        List<AnalyticsEntity> analyticsByRoom = analyticsService.getAnalyticsByRoomId(roomId);
        return new ResponseEntity<>(analyticsByRoom, HttpStatus.OK);
    }
}
