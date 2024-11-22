package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.RoomEntity;
import com.jizas.statcheck.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Get all rooms
    public List<RoomEntity> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get room by ID
    public Optional<RoomEntity> getRoomById(Long roomID) {
        return roomRepository.findById(roomID);
    }

    // Create or update room
    public RoomEntity saveRoom(RoomEntity roomEntity) {
        return roomRepository.save(roomEntity);
    }

    // Delete room by ID
    public void deleteRoom(Long roomID) {
        roomRepository.deleteById(roomID);
    }
}
