package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.RoomEntity;
import com.jizas.statcheck.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomEntity> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomEntity getRoom(@PathVariable Long id) {
        return roomService.getRoom(id);
    }

    @GetMapping("/building/{buildingId}")
    public List<RoomEntity> getRoomsByBuilding(@PathVariable Long buildingId) {
        return roomService.getRoomsByBuilding(buildingId);
    }

    @PostMapping
    public RoomEntity createRoom(@RequestBody RoomEntity room) {
        return roomService.saveRoom(room);
    }

    @PutMapping("/{id}")
    public RoomEntity updateRoom(@PathVariable Long id, @RequestBody RoomEntity room) {
        room.setRoomID(id);
        return roomService.saveRoom(room);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}