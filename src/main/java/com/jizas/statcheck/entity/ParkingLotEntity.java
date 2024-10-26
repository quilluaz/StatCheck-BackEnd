package com.jizas.statcheck.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_lot")
public class ParkingLotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Long lotId;

    @Column(name = "lot_number", nullable = false)
    private String lotNumber;

    @Column(name = "total_spaces", nullable = false)
    private int totalSpaces;

    @ElementCollection // This annotation is used for collections of basic types
    @Column(name = "available_spaces")
    private List<String> availableSpaces = new ArrayList<>();

    // Other fields, constructors, getters and setters...

    // Default constructor
    public ParkingLotEntity() {
        // Required by JPA
    }

    // Parameterized constructor
    public ParkingLotEntity(String lotNumber, int totalSpaces) {
        this.lotNumber = lotNumber;
        this.totalSpaces = totalSpaces;
        initializeAvailableSpaces();
    }

    // Initialize available spaces based on totalSpaces
    private void initializeAvailableSpaces() {
        availableSpaces.clear(); // Clear previous spaces if any
        for (int i = 1; i <= totalSpaces; i++) {
            availableSpaces.add(lotNumber + i);  // e.g., "Lot A1", "Lot A2"
        }
    }

    // Getters and Setters
    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public void setTotalSpaces(int totalSpaces) {
        this.totalSpaces = totalSpaces;
        initializeAvailableSpaces();  // Update available spaces when total spaces change
    }

    public List<String> getAvailableSpaces() {
        return availableSpaces;
    }

    // Setter to allow updating available spaces
    public void setAvailableSpaces(List<String> availableSpaces) {
        this.availableSpaces = availableSpaces;
    }
}
