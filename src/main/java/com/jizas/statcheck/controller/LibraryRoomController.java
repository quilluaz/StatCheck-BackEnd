package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.LibraryRoomEntity;
import com.jizas.statcheck.service.LibraryRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-rooms")
public class LibraryRoomController {

    @Autowired
    private LibraryRoomService libraryRoomService;

    // Get all library rooms
    @GetMapping
    public List<LibraryRoomEntity> getAllLibraryRooms() {
        return libraryRoomService.getAllLibraryRooms();
    }

    // Get a specific library room by its ID
    @GetMapping("/{libraryRoomID}")
    public LibraryRoomEntity getLibraryRoomById(@PathVariable Long libraryRoomID) {
        return libraryRoomService.getLibraryRoomById(libraryRoomID);
    }

    // Create a new library room
    @PostMapping("/")
    public LibraryRoomEntity createLibraryRoom(@RequestBody LibraryRoomEntity libraryRoomEntity) {
        return libraryRoomService.createLibraryRoom(libraryRoomEntity);
    }

    // Update an existing library room
    @PutMapping("/{libraryRoomID}")
    public LibraryRoomEntity updateLibraryRoom(@PathVariable Long libraryRoomID, @RequestBody LibraryRoomEntity libraryRoomEntity) {
        return libraryRoomService.updateLibraryRoom(libraryRoomID, libraryRoomEntity);
    }

    // Delete a library room by its ID
    @DeleteMapping("/{libraryRoomID}")
    public void deleteLibraryRoom(@PathVariable Long libraryRoomID) {
        libraryRoomService.deleteLibraryRoom(libraryRoomID);
    }
}
