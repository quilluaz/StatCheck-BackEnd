package com.jizas.statcheck.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_reservation")
public class ParkingReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "user_id")  // This will be null
    private Long userId;

    @Column(name = "reservation_start_time", nullable = false)
    private LocalDateTime reservationStartTime;

    @Column(name = "reservation_end_time", nullable = false)
    private LocalDateTime reservationEndTime;

    @Column(name = "reservation_status", nullable = false)
    private String reservationStatus;

    @Column(name = "space_number", nullable = false)
    private String spaceNumber;  // To store assigned space

    @ManyToOne
    @JoinColumn(name = "lot_id", nullable = false)  // Foreign key to ParkingLot
    private ParkingLotEntity parkingLot;

    // Default constructor
    public ParkingReservationEntity() {
        // Required by JPA
    }

    // Parameterized constructor
    public ParkingReservationEntity(LocalDateTime reservationStartTime, LocalDateTime reservationEndTime,
                                     String reservationStatus, String spaceNumber, ParkingLotEntity parkingLot) {
        this.reservationStartTime = reservationStartTime;
        this.reservationEndTime = reservationEndTime;
        this.reservationStatus = reservationStatus;
        this.spaceNumber = spaceNumber;
        this.parkingLot = parkingLot;
    }

    // Getters and Setters
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;  // Can be set to null
    }

    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    public void setReservationStartTime(LocalDateTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }

    public LocalDateTime getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(LocalDateTime reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getSpaceNumber() {
        return spaceNumber;
    }

    public void setSpaceNumber(String spaceNumber) {
        this.spaceNumber = spaceNumber;
    }

    public ParkingLotEntity getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLotEntity parkingLot) {
        this.parkingLot = parkingLot;
    }
}
