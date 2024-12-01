package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_reservation")
public class ParkingReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationID;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_space_id")
    @JsonIgnoreProperties({"reservations", "hibernateLazyInitializer"})
    private ParkingSpaceEntity parkingSpaceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"reservations", "password", "hibernateLazyInitializer"})
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    @JsonIgnoreProperties({"parkingSpaces", "hibernateLazyInitializer"})
    private ParkingLotEntity parkingLotEntity;

    // Default constructor
    public ParkingReservationEntity() {}

    // Constructor with fields
    public ParkingReservationEntity(LocalDateTime startTime, LocalDateTime endTime, String status, ParkingSpaceEntity parkingSpaceEntity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.parkingSpaceEntity = parkingSpaceEntity;
    }

    // Getters and Setters
    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ParkingSpaceEntity getParkingSpaceEntity() {
        return parkingSpaceEntity;
    }

    public void setParkingSpaceEntity(ParkingSpaceEntity parkingSpaceEntity) {
        this.parkingSpaceEntity = parkingSpaceEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ParkingLotEntity getParkingLotEntity() {
        return parkingLotEntity;
    }

    public void setParkingLotEntity(ParkingLotEntity parkingLotEntity) {
        this.parkingLotEntity = parkingLotEntity;
    }
}
