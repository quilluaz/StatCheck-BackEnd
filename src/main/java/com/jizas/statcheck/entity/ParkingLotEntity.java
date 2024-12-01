package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_lot")
public class ParkingLotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parkingLotID")
    private Long parkingLotID;

    @Column(name = "parkingLotName")
    private String parkingLotName;

    @Column(name = "spaces")
    private int spaces;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ParkingSpaceEntity> parkingSpaces = new ArrayList<>();

    // Getters and Setters
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
    }

    public List<ParkingSpaceEntity> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpaceEntity> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

}
