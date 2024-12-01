package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "building")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "buildingID")
public class BuildingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private int buildingID;

    @Column(name = "building_name", nullable = false)
    @NotBlank(message = "Building name must not be empty")
    private String buildingName;

    @Min(value = 1, message = "Floors must be at least 1")
    @Column(name = "floors", nullable = false)
    private int floors = 1;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"building", "hibernateLazyInitializer"})
    private List<RoomEntity> rooms = new ArrayList<>();

    // Default constructor
    public BuildingEntity() {}

    // Constructor with fields
    public BuildingEntity(String buildingName, int floors) {
        this.buildingName = buildingName;
        this.floors = floors;
    }

    // Getters and Setters
    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }
}
