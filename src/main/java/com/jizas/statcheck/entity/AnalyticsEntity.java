package com.jizas.statcheck.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalTime;
import java.time.LocalDate;

@Entity
@Table(name = "analytics")
public class AnalyticsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analyticsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private RoomEntity room;

    @NotNull(message = "Usage rate is required")
    @Min(value = 0, message = "Usage rate must be at least 0")
    @Max(value = 100, message = "Usage rate cannot exceed 100")
    @Column(nullable = false)
    private Integer usageRate;

    @Column(name = "peak_hours")
    private LocalTime peakHours;

    @NotNull(message = "Date generated is required")
    @Column(name = "date_generated", nullable = false)
    private LocalDate dateGenerated;

    @Column(name = "created_by")
    private String createdBy;

    // Constructors
    public AnalyticsEntity() {}

    public AnalyticsEntity(RoomEntity room, Integer usageRate, LocalTime peakHours, LocalDate dateGenerated) {
        this.room = room;
        this.usageRate = usageRate;
        this.peakHours = peakHours;
        this.dateGenerated = dateGenerated;
    }

    // Getters and Setters
    public Long getAnalyticsID() {
        return analyticsID;
    }

    public void setAnalyticsID(Long analyticsID) {
        this.analyticsID = analyticsID;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public Integer getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(Integer usageRate) {
        this.usageRate = usageRate;
    }

    public LocalTime getPeakHours() {
        return peakHours;
    }

    public void setPeakHours(LocalTime peakHours) {
        this.peakHours = peakHours;
    }

    public LocalDate getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(LocalDate dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @PrePersist
    protected void onCreate() {
        if (dateGenerated == null) {
            dateGenerated = LocalDate.now();
        }
    }
} 