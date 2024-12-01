package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.LibraryEntity;
import com.jizas.statcheck.entity.LibraryRoomEntity;
import com.jizas.statcheck.repository.LibraryRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibraryRoomService {
    
    @Autowired
    private LibraryRoomRepository libraryRoomRepository;
    
    @Autowired
    private LibraryService libraryService;

    public List<LibraryRoomEntity> getAllRoomsByLibrary(Long libraryId) {
        return libraryRoomRepository.findByLibraryLibraryID(libraryId);
    }

    @Transactional
    public LibraryRoomEntity createRoom(LibraryRoomEntity room) {
        if (room.getLibrary() == null || room.getLibrary().getLibraryID() == null) {
            throw new IllegalArgumentException("Library ID is required");
        }
        
        LibraryEntity library = libraryService.getLibraryById(room.getLibrary().getLibraryID());
        room.setLibrary(library);
        
        if (room.getStatus() == null) {
            room.setStatus("AVAILABLE");
        }
        
        return libraryRoomRepository.save(room);
    }

    public LibraryRoomEntity updateRoom(Long roomId, LibraryRoomEntity updatedRoom) {
        LibraryRoomEntity existingRoom = libraryRoomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
        
        existingRoom.setRoomName(updatedRoom.getRoomName());
        existingRoom.setStatus(updatedRoom.getStatus());
        existingRoom.setAvailableTimeSlots(updatedRoom.getAvailableTimeSlots());
        
        return libraryRoomRepository.save(existingRoom);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        if (!libraryRoomRepository.existsById(roomId)) {
            throw new RuntimeException("Room not found with id: " + roomId);
        }
        libraryRoomRepository.deleteById(roomId);
    }

    public LibraryRoomEntity getRoomById(Long roomId) {
        return libraryRoomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
    }

    public List<LibraryRoomEntity> getAllRooms() {
        return libraryRoomRepository.findAll();
    }
}
