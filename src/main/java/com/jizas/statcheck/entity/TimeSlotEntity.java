package com.jizas.statcheck.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "timeSlotId")
public class TimeSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeSlotId;

    private String startTime;
    private String endTime;


     // Many-to-One relationship with ScheduleEntity

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduleId", nullable = false)
    //@JsonBackReference
    //@JsonIgnore

    private ScheduleEntity schedule;
   

    // Many-to-One relationship with SubjectEntity

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subjectId", nullable = false)
    //@JsonBackReference
    //@JsonIgnore
    private SubjectEntity subject;


   
    public TimeSlotEntity() {}


    public TimeSlotEntity(String startTime, String endTime, SubjectEntity subject, ScheduleEntity schedule) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.schedule = schedule;
    }

    // Getters and Setters
    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }
}
    