package com.jizas.statcheck.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId; // Primary Key

    // Nullable for now, will map to a User entity later
    @Column(name = "UserID", nullable = true)
    private int userId; 

    // Foreign Key relationship with AnalyticsEntity
    @ManyToOne
    @JoinColumn(name = "AnalyticsID", nullable = true) // Nullable, as some notifications may not have analytics data
    private AnalyticsEntity analytics; // This links to AnalyticsEntity

    @Column(length = 255)
    private String message; // Message text

    private LocalDateTime dateSent; // Timestamp for the date and time when the notification is sent

    private int status; // TINYINT for status (0: Unread, 1: Read)

    // Default constructor
    public NotificationEntity() {
        super();
    }

    public NotificationEntity(int userId, AnalyticsEntity analytics, String message, LocalDateTime dateSent, int status) {
        this.userId = userId;
        this.analytics = analytics;
        this.message = message;
        this.dateSent = dateSent;
        this.status = status;
    }

    // Getters and Setters

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public AnalyticsEntity getAnalytics() {
        return analytics;
    }

    public void setAnalytics(AnalyticsEntity analytics) {
        this.analytics = analytics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
