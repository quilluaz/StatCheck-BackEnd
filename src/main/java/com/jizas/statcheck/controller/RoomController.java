package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.RoomEntity;
import com.jizas.statcheck.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Get all rooms
    @GetMapping
    public List<RoomEntity> getAllRooms() {
        return roomService.getAllRooms();
    }

    // Get room by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomEntity> getRoomById(@PathVariable("id") Long roomID) {
        Optional<RoomEntity> roomEntity = roomService.getRoomById(roomID);
        if (roomEntity.isPresent()) {
            return ResponseEntity.ok(roomEntity.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Create a new room
    @PostMapping
    public ResponseEntity<RoomEntity> createRoom(@RequestBody RoomEntity roomEntity) {
        RoomEntity createdRoom = roomService.saveRoom(roomEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    // Update room
    @PutMapping("/{id}")
    public ResponseEntity<RoomEntity> updateRoom(@PathVariable("id") Long roomID, @RequestBody RoomEntity roomEntity) {
        Optional<RoomEntity> existingRoom = roomService.getRoomById(roomID);
        if (existingRoom.isPresent()) {
            roomEntity.setRoomId(roomID);
            RoomEntity updatedRoom = roomService.saveRoom(roomEntity);
            return ResponseEntity.ok(updatedRoom);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete room
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") Long roomID) {
        Optional<RoomEntity> existingRoom = roomService.getRoomById(roomID);
        if (existingRoom.isPresent()) {
            roomService.deleteRoom(roomID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
