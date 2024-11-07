package com.jizas.statcheck.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizas.statcheck.entity.AnalyticsEntity;
import com.jizas.statcheck.entity.NotificationEntity;
import com.jizas.statcheck.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<NotificationEntity> getAllNotifications() {
        return notificationRepository.findAll(); // Assuming you have a notification repository
    }
    
    // Get all notifications for a user
    public List<NotificationEntity> getNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserId(userId);
    }

    // Get a single notification by ID
    public Optional<NotificationEntity> getNotificationById(int notificationId) {
        return notificationRepository.findById(notificationId);
    }

    // Send a notification, with optional Analytics reference
    public NotificationEntity sendNotification(Integer userId, String message, AnalyticsEntity analytics) {
        NotificationEntity notification = new NotificationEntity();
        notification.setUserId(userId);  // Set nullable userId
        notification.setMessage(message);
        notification.setDateSent(LocalDateTime.now());
        notification.setStatus(0); // 0: Unread
        notification.setAnalytics(analytics); // Set the Analytics entity, can be null
        return notificationRepository.save(notification);
    }

    // Mark a notification as read
    public NotificationEntity markAsRead(int notificationId) {
        Optional<NotificationEntity> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isPresent()) {
            NotificationEntity notification = notificationOptional.get();
            notification.setStatus(1); // 1: Read
            return notificationRepository.save(notification);
        }
        return null;
    }

    // Update a notification
    public NotificationEntity updateNotification(int notificationId, Integer userId, String message, AnalyticsEntity analytics) {
    Optional<NotificationEntity> optionalNotification = notificationRepository.findById(notificationId);
    if (optionalNotification.isPresent()) {
        NotificationEntity notification = optionalNotification.get();
        // Update fields
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setAnalytics(analytics); // Update the analytics link if provided
        return notificationRepository.save(notification); // Save and return the updated notification
    } else {
        throw new NoSuchElementException("Notification not found");
    }
}

    // Delete a notification by ID
    public void deleteNotification(int notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    // Get unread notifications for a user
    public List<NotificationEntity> getUnreadNotifications(Integer userId) {
        return notificationRepository.findByUserIdAndStatus(userId, 0); // 0: Unread
    }
}
