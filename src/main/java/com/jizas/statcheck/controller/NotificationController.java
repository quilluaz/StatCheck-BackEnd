package com.jizas.statcheck.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import com.jizas.statcheck.entity.NotificationEntity;
import com.jizas.statcheck.service.AnalyticsService;
import com.jizas.statcheck.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AnalyticsService analyticsService;

    // Simple print method to test the controller
    @GetMapping("/print")
    public String print() {
        return "Hello, Notifications!";
    }

    // Get all notifications
    @GetMapping("/getAll")
    public ResponseEntity<List<NotificationEntity>> getAllNotifications() {
        List<NotificationEntity> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // Create (Send) a notification, optionally linking it to an Analytics record
    @PostMapping("/sendNotification")
    public ResponseEntity<NotificationEntity> sendNotification(@RequestBody NotificationEntity notificationEntity) {
        // Check if an AnalyticsEntity exists if analyticsId is provided
        AnalyticsEntity analytics = null;
        if (notificationEntity.getAnalytics() != null) {
            int analyticsId = notificationEntity.getAnalytics().getAnalyticsId();
            Optional<AnalyticsEntity> optionalAnalytics = analyticsService.getAnalyticsById(analyticsId);
            if (optionalAnalytics.isPresent()) {
                analytics = optionalAnalytics.get();
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid Analytics ID
            }
        }

        // Send notification
        NotificationEntity newNotification = notificationService.sendNotification(
            notificationEntity.getUserId(),
            notificationEntity.getMessage(),
            analytics // Include analytics in the service call
        );
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

    // Get all notifications for a specific user
    @GetMapping("/getNotificationsByUser/{userId}")
    public ResponseEntity<List<NotificationEntity>> getNotificationsByUserId(@PathVariable Integer userId) {
        List<NotificationEntity> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // Get a specific notification by ID
    @GetMapping("/getNotificationById/{notificationId}")
    public ResponseEntity<NotificationEntity> getNotificationById(@PathVariable int notificationId) {
        try {
            NotificationEntity notification = notificationService.getNotificationById(notificationId).orElseThrow();
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Mark a notification as read
    @PutMapping("/markAsRead/{notificationId}")
    public ResponseEntity<NotificationEntity> markAsRead(@PathVariable int notificationId) {
        try {
            NotificationEntity updatedNotification = notificationService.markAsRead(notificationId);
            return updatedNotification != null ? new ResponseEntity<>(updatedNotification, HttpStatus.OK)
                                               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a notification
    @PutMapping("/update/{notificationId}")
public ResponseEntity<NotificationEntity> updateNotification(@PathVariable int notificationId, 
                                                              @RequestBody NotificationEntity updatedNotification) {
    try {
        // Check if an AnalyticsEntity exists if analyticsId is provided
        AnalyticsEntity analytics = null;
        if (updatedNotification.getAnalytics() != null) {
            int analyticsId = updatedNotification.getAnalytics().getAnalyticsId();
            Optional<AnalyticsEntity> optionalAnalytics = analyticsService.getAnalyticsById(analyticsId);
            if (optionalAnalytics.isPresent()) {
                analytics = optionalAnalytics.get();
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid Analytics ID
            }
        }

        // Update the notification
        NotificationEntity notification = notificationService.updateNotification(notificationId,
                updatedNotification.getUserId(),
                updatedNotification.getMessage(),
                analytics); // Include analytics in the service call

        return new ResponseEntity<>(notification, HttpStatus.OK);
    } catch (NoSuchElementException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Notification not found
    }
}

    // Delete a notification by ID
    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable int notificationId) {
        try {
            notificationService.deleteNotification(notificationId);
            return new ResponseEntity<>("Notification successfully deleted", HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>("Notification not found", HttpStatus.NOT_FOUND);
        }
    }

    // Get unread notifications for a user
    @GetMapping("/getUnreadNotifications/{userId}")
    public ResponseEntity<List<NotificationEntity>> getUnreadNotifications(@PathVariable Integer userId) {
        List<NotificationEntity> unreadNotifications = notificationService.getUnreadNotifications(userId);
        return new ResponseEntity<>(unreadNotifications, HttpStatus.OK);
    }
}
