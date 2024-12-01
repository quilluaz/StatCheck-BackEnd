package com.jizas.statcheck.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LibraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id")
    private Long libraryID;

    @Column(name = "library_name")
    private String libraryName;

    @Column(name = "rooms")
    private int rooms;

    // One-to-many relationship with LibraryRoomEntity
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LibraryRoomEntity> libraryRooms = new ArrayList<>();

    // Getters and Setters
    public Long getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(Long libraryID) {
        this.libraryID = libraryID;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
        initializeLibraryRooms(); // Automatically initialize rooms when setting the count
    }

    public List<LibraryRoomEntity> getLibraryRooms() {
        return libraryRooms;
    }

    public void setLibraryRooms(List<LibraryRoomEntity> libraryRooms) {
        this.libraryRooms = libraryRooms;
    }

    // Helper method to initialize library rooms based on the number of rooms
    private void initializeLibraryRooms() {
        libraryRooms.clear(); // Clear existing rooms to avoid duplication
        for (int i = 1; i <= rooms; i++) {
            LibraryRoomEntity room = new LibraryRoomEntity();
            room.setRoomName("Room " + i); // Changed from setLibraryRoomName to setRoomName
            room.setStatus("Available"); // Default status
            room.setLibrary(this); // Associate the room with the library
            libraryRooms.add(room);
        }
    }
}
