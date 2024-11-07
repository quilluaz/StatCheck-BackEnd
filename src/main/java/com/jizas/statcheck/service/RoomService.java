package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.BuildingEntity;
import com.jizas.statcheck.entity.RoomEntity;
import com.jizas.statcheck.repository.BuildingRepository;
import com.jizas.statcheck.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    public List<RoomEntity> getAllRooms() {
        return roomRepository.findAll();
    }

    public RoomEntity getRoom(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Room not found"));
    }

    public RoomEntity saveRoom(RoomEntity room) {
        if (room.getBuilding() != null && room.getBuilding().getBldgID() != null) {
            BuildingEntity building = buildingRepository.findById(room.getBuilding().getBldgID())
                    .orElseThrow(() -> new RuntimeException("Building not found"));
            room.setBuilding(building);
        }

        if (room.getRoomID() != null) {
            roomRepository.findById(room.getRoomID())
                    .orElseThrow(() -> new RuntimeException("Room not found"));
        }

        return roomRepository.save(room);
    }

    public List<RoomEntity> getRoomsByBuilding(Long buildingId) {
        return roomRepository.findByBuilding_BldgID(buildingId);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}