package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.jizas.statcheck.entity.BuildingEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "room")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roomID")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomID;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "current_capacity")
    private int currentCapacity;

    @Column(name = "availability_status", nullable = false)
    private String availabilityStatus;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    @JsonIgnoreProperties({"floors", "rooms"})
    private BuildingEntity building;

    @Column(name = "floor", nullable = false)
    private int floorNumber;

    public RoomEntity() {}

    public RoomEntity(String roomType, int capacity, int currentCapacity, String availabilityStatus, BuildingEntity building, int floorNumber) {
        this.roomType = roomType;
        this.capacity = capacity;
        this.currentCapacity = currentCapacity;
        this.availabilityStatus = availabilityStatus;
        this.building = building;
        this.floorNumber = floorNumber;
    }

    // Getters and setters
    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
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
}
