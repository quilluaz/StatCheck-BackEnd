package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "libraryRoomID")
public class LibraryRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_room_id")
    private Long libraryRoomID;

    @Column(name = "library_room_name")
    private String libraryRoomName;

    @Column(name = "status")
    private String status;

    // Many-to-one relationship with LibraryEntity
    @ManyToOne
    @JoinColumn(name = "library_id")
    @JsonIgnoreProperties({"libraryID", "rooms", "libraryRooms"})
    private LibraryEntity library;

    @OneToMany(mappedBy = "libraryRoomEntity", cascade = CascadeType.ALL)
    private List<LibraryRoomReservationEntity> reservations;
    // Getters and Setters
    public Long getLibraryRoomID() {
        return libraryRoomID;
    }

    public void setLibraryRoomID(Long libraryRoomID) {
        this.libraryRoomID = libraryRoomID;
    }

    public String getLibraryRoomName() {
        return libraryRoomName;
    }

    public void setLibraryRoomName(String libraryRoomName) {
        this.libraryRoomName = libraryRoomName;
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

    public List<LibraryRoomReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<LibraryRoomReservationEntity> reservations) {
        this.reservations = reservations;
    }
}

