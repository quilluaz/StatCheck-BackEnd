package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnoreProperties({"schedules"})
    private RoomEntity roomEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonIgnoreProperties({"schedules"})
    private SubjectEntity subjectEntity;

    // Default constructor
    public ScheduleEntity() {}

    // Constructor with fields
    public ScheduleEntity(String dayOfWeek, LocalDateTime startTime, LocalDateTime endTime,
                          RoomEntity roomEntity, SubjectEntity subjectEntity) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomEntity = roomEntity;
        this.subjectEntity = subjectEntity;
    }

    // Getters and Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    public SubjectEntity getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }
}