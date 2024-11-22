package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.sql.Time;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "scheduleID")
@Table(name = "schedule")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleID;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @OneToMany(mappedBy = "scheduleEntity", cascade = CascadeType.ALL)
    private List<TimeSlotEntity> timeslots;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity roomEntity;

    public ScheduleEntity() {}

    public ScheduleEntity(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    // Getters and Setters
    public Long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<TimeSlotEntity> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<TimeSlotEntity> timeslots) {
        this.timeslots = timeslots;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }
}
