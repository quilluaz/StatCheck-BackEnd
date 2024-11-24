package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jizas.statcheck.controller.ScheduleController;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "timeSlotID")
@Table(name = "time_slot")
public class TimeSlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
    private Long timeSlotID;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "schedule_id ", nullable = false)
    private ScheduleEntity scheduleEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subjectEntity;

    // Default constructor
    public TimeSlotEntity() {}

    // Constructor with fields
    public TimeSlotEntity(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public Long getTimeSlotID() {
        return timeSlotID;
    }

    public void setTimeSlotID(Long timeSlotID) {
        this.timeSlotID = timeSlotID;
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

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public SubjectEntity getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }
}
