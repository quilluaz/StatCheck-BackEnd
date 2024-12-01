package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "libraryRoomReservationID")
@Table(name = "library_room_reservation")
public class LibraryRoomReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_room_reservation_id")
    private Long libraryRoomReservationID;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "library_room_id")
    private LibraryRoomEntity libraryRoomEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    // Default constructor
    public LibraryRoomReservationEntity() {}

    // Constructor with fields
    public LibraryRoomReservationEntity(LocalDateTime startTime, LocalDateTime endTime, String status, LibraryRoomEntity libraryRoomEntity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.libraryRoomEntity = libraryRoomEntity;
    }

    // Getters and Setters
    public Long getLibraryRoomReservationID() {
        return libraryRoomReservationID;
    }

    public void setLibraryRoomReservationID(Long libraryRoomReservationID) {
        this.libraryRoomReservationID = libraryRoomReservationID;
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

    public LibraryRoomEntity getLibraryRoomEntity() {
        return libraryRoomEntity;
    }

    public void setLibraryRoomEntity(LibraryRoomEntity libraryRoomEntity) {
        this.libraryRoomEntity = libraryRoomEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
