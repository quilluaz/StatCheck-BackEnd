package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "current_capacity")
    private int currentCapacity;

    @Column(name = "availability_status", nullable = false)
    private String availabilityStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    @JsonIgnoreProperties({"rooms", "hibernateLazyInitializer"})
    private BuildingEntity building;

    @Column(name = "floor", nullable = false)
    private int floorNumber;

    @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"roomEntity"})
    private List<ScheduleEntity> schedules;

    public RoomEntity() {}

    public RoomEntity(String roomType, int capacity, int currentCapacity, String availabilityStatus,
                      BuildingEntity building, int floorNumber) {
        this.roomType = roomType;
        this.capacity = capacity;
        this.currentCapacity = currentCapacity;
        this.availabilityStatus = availabilityStatus;
        this.building = building;
        this.floorNumber = floorNumber;
    }

    // Getters and setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public BuildingEntity getBuilding() {
        return building;
    }

    public void setBuilding(BuildingEntity building) {
        this.building = building;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public List<ScheduleEntity> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleEntity> schedules) {
        this.schedules = schedules;
    }
}
