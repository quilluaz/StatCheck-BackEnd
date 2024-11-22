package com.jizas.statcheck.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ParkingLotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingLotID;

    private String parkingLotName;

    private int spaces; // New field to define the number of parking spaces

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSpaceEntity> parkingSpaces = new ArrayList<>();

    public ParkingLotEntity() {
    }

    public ParkingLotEntity(String parkingLotName, int spaces) {
        this.parkingLotName = parkingLotName;
        this.spaces = spaces;
        initializeParkingSpaces();
    }

    public Long getParkingLotID() {
        return parkingLotID;
    }

    public void setParkingLotID(Long parkingLotID) {
        this.parkingLotID = parkingLotID;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
        initializeParkingSpaces();
    }

    public List<ParkingSpaceEntity> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpaceEntity> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    // Initialize parking spaces based on the spaces count
    private void initializeParkingSpaces() {
        parkingSpaces.clear();
        for (int i = 1; i <= spaces; i++) {
            // Alternate between 'motor' and 'car' for demo purposes
            String spaceType = (i % 2 == 0) ? "car" : "motor";
            ParkingSpaceEntity space = new ParkingSpaceEntity("Space " + i, "available", spaceType, this);
            parkingSpaces.add(space);
        }
    }

}
