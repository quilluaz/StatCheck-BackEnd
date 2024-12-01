package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "library_room_entity")
public class LibraryRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_room_id")
    private Long libraryRoomID;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    @JsonBackReference
    private LibraryEntity library;

    @ElementCollection
    @CollectionTable(
        name = "library_room_time_slots",
        joinColumns = @JoinColumn(
            name = "library_room_id",
            referencedColumnName = "library_room_id"
        )
    )
    @Column(name = "time_slot")
    private List<String> availableTimeSlots = new ArrayList<>();

    // Getters and Setters
    public Long getLibraryRoomID() {
        return libraryRoomID;
    }

    public void setLibraryRoomID(Long libraryRoomID) {
        this.libraryRoomID = libraryRoomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LibraryEntity getLibrary() {
        return library;
    }

    public void setLibrary(LibraryEntity library) {
        this.library = library;
    }

    public List<String> getAvailableTimeSlots() {
        return availableTimeSlots;
    }

    public void setAvailableTimeSlots(List<String> availableTimeSlots) {
        this.availableTimeSlots = availableTimeSlots;
    }

    public String getLibraryName() {
        return library != null ? library.getLibraryName() : null;
    }
}

