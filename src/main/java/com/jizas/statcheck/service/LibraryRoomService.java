package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.LibraryRoomEntity;
import com.jizas.statcheck.repository.LibraryRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryRoomService {

    @Autowired
    private LibraryRoomRepository libraryRoomRepository;

    // Get all library rooms
    public List<LibraryRoomEntity> getAllLibraryRooms() {
        return libraryRoomRepository.findAll();
    }

    // Get a specific library room by ID
    public LibraryRoomEntity getLibraryRoomById(Long libraryRoomID) {
        Optional<LibraryRoomEntity> libraryRoom = libraryRoomRepository.findById(libraryRoomID);
        return libraryRoom.orElse(null);
    }

    // Create a new library room
    public LibraryRoomEntity createLibraryRoom(LibraryRoomEntity libraryRoomEntity) {
        return libraryRoomRepository.save(libraryRoomEntity);
    }

    // Update an existing library room
    public LibraryRoomEntity updateLibraryRoom(Long libraryRoomID, LibraryRoomEntity libraryRoomEntity) {
        Optional<LibraryRoomEntity> existingRoom = libraryRoomRepository.findById(libraryRoomID);
        if (existingRoom.isPresent()) {
            LibraryRoomEntity room = existingRoom.get();
            room.setLibraryRoomName(libraryRoomEntity.getLibraryRoomName());
            room.setStatus(libraryRoomEntity.getStatus());
            return libraryRoomRepository.save(room);
        }
        return null;
    }

    // Delete a library room by ID
    public void deleteLibraryRoom(Long libraryRoomID) {
        libraryRoomRepository.deleteById(libraryRoomID);
    }
}
