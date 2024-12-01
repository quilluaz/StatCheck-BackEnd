package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.LibraryRoomEntity;
import com.jizas.statcheck.service.LibraryRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/library-rooms")
public class LibraryRoomController {

    @Autowired
    private LibraryRoomService libraryRoomService;

    @GetMapping("/library/{libraryId}")
    public ResponseEntity<List<LibraryRoomEntity>> getRoomsByLibrary(@PathVariable Long libraryId) {
        List<LibraryRoomEntity> rooms = libraryRoomService.getAllRoomsByLibrary(libraryId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<LibraryRoomEntity> getRoomById(@PathVariable Long roomId) {
        LibraryRoomEntity room = libraryRoomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody LibraryRoomEntity room) {
        try {
            LibraryRoomEntity createdRoom = libraryRoomService.createRoom(room);
            return new ResponseEntity<>(
                Map.of(
                    "libraryRoomID", createdRoom.getLibraryRoomID(),
                    "roomName", createdRoom.getRoomName(),
                    "status", createdRoom.getStatus(),
                    "libraryId", createdRoom.getLibrary().getLibraryID(),
                    "availableTimeSlots", createdRoom.getAvailableTimeSlots()
                ), 
                HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                Map.of("error", e.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<LibraryRoomEntity> updateRoom(
            @PathVariable Long roomId,
            @RequestBody LibraryRoomEntity room) {
        LibraryRoomEntity updatedRoom = libraryRoomService.updateRoom(roomId, room);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        libraryRoomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LibraryRoomEntity>> getAllRooms() {
        List<LibraryRoomEntity> rooms = libraryRoomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
}
