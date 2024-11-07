package com.jizas.statcheck.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "scheduleId")
@Table(name = "SCHEDULE")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;

    //@Column(name = "roomID", nullable = true)
    private String roomID;


    private String dayOfWeek;


    // One-to-Many relationship with TimeSlotEntity
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JsonManagedReference
    private List<TimeSlotEntity> timeSlots;
    // Default constructor

    
    public ScheduleEntity() {
        super();
    }


    public ScheduleEntity(String roomID, String dayOfWeek) {
        this.roomID = roomID;
        this.dayOfWeek = dayOfWeek;
    }

 
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getRoomID(){
        return roomID;
    }

    public void setRoomID(String roomID){
        this.roomID = roomID;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<TimeSlotEntity> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotEntity> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
