package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_space_entity")
public class ParkingSpaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingSpaceId;

    private String parkingName;
    private String status;
    private String spaceType;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    @JsonBackReference
    private ParkingLotEntity parkingLot;

    @OneToMany(mappedBy = "parkingSpaceEntity", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("parkingSpaceEntity")
    private List<ParkingReservationEntity> reservations = new ArrayList<>();

    public ParkingSpaceEntity() {
    }

    public ParkingSpaceEntity(String parkingName, String status, String spaceType, ParkingLotEntity parkingLot) {
        this.parkingName = parkingName;
        this.status = status;
        this.spaceType = spaceType;
        this.parkingLot = parkingLot;
    }

    // Getters and Setters
    public Long getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(Long parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public ParkingLotEntity getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLotEntity parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<ParkingReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ParkingReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Lazy status calculation: only update when status is accessed
    public String getAvailability() {
        if (this.status == null) { // Status hasn't been set yet, calculate it
            updateStatus();
        }
        return status;
    }

    // Method to update the parking space status based on current time and reservations
    public void updateStatus() {
        LocalDateTime currentTime = LocalDateTime.now();
        boolean isReserved = false;

        // Check for any active reservation based on current time
        for (ParkingReservationEntity reservation : reservations) {
            // Check if the current time is within the reservation start and end time
            if (currentTime.isAfter(reservation.getStartTime()) && currentTime.isBefore(reservation.getEndTime())) {
                isReserved = true;
                break; // No need to check further if one reservation matches
            }
        }

        // Set the status based on whether the space is reserved or not
        this.status = isReserved ? "reserved" : "available";
    }

    public void setAvailability(String availability) {
        this.status = availability;
    }
}
