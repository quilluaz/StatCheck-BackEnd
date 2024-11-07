package com.jizas.statcheck.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Analytics")
public class AnalyticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int analyticsId; // Primary Key

    @Column(name = "RoomID", nullable = true)
    private int roomId; // Nullable Room ID as a placeholder

    @Column
    private float usageRate; // Percentage of usage rate

    @Column(length = 50)
    private String peakHours; // Peak hours

    private LocalDateTime dateGenerated; // Date and time generated

    // One-to-many relationship with NotificationEntity
    @OneToMany(mappedBy = "analytics", cascade = CascadeType.ALL)
    private List<NotificationEntity> notifications;

    // Default constructor
    public AnalyticsEntity() {
        super();
    }

    public AnalyticsEntity(int roomId, float usageRate, String peakHours, LocalDateTime dateGenerated) {
        this.roomId = roomId;
        this.usageRate = usageRate;
        this.peakHours = peakHours;
        this.dateGenerated = dateGenerated;
    }

    // Getters and Setters

    public int getAnalyticsId() {
        return analyticsId;
    }

    public void setAnalyticsId(int analyticsId) {
        this.analyticsId = analyticsId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public float getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(float usageRate) {
        this.usageRate = usageRate;
    }

    public String getPeakHours() {
        return peakHours;
    }

    public void setPeakHours(String peakHours) {
        this.peakHours = peakHours;
    }

    public LocalDateTime getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(LocalDateTime dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public List<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationEntity> notifications) {
        this.notifications = notifications;
    }
}
