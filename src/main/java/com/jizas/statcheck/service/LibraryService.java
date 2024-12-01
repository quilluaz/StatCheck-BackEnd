package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.LibraryEntity;
import com.jizas.statcheck.repository.LibraryRepository;
import com.jizas.statcheck.entity.LibraryRoomEntity;
import com.jizas.statcheck.repository.LibraryRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private LibraryRoomRepository libraryRoomRepository;

    // Get all libraries
    public List<LibraryEntity> getAllLibraries() {
        return libraryRepository.findAll();
    }

    // Get a specific library by ID
    public LibraryEntity getLibraryById(Long libraryID) {
        return libraryRepository.findById(libraryID)
            .orElseThrow(() -> new RuntimeException("Library not found with id: " + libraryID));
    }

    // Create a new library with rooms
    @Transactional
    public LibraryEntity createLibrary(LibraryEntity libraryEntity) {
        if (libraryEntity.getRooms() > 0) {
            libraryEntity.setRooms(libraryEntity.getRooms());
        }
        return libraryRepository.save(libraryEntity);
    }

    // Update existing library
    @Transactional
    public LibraryEntity updateLibrary(Long libraryID, LibraryEntity updatedLibrary) {
        LibraryEntity existingLibrary = libraryRepository.findById(libraryID)
            .orElseThrow(() -> new RuntimeException("Library not found with id: " + libraryID));

        existingLibrary.setLibraryName(updatedLibrary.getLibraryName());
        
        if (existingLibrary.getRooms() != updatedLibrary.getRooms()) {
            existingLibrary.setRooms(updatedLibrary.getRooms());
        }

        return libraryRepository.save(existingLibrary);
    }

    // Delete library and its rooms
    @Transactional
    public void deleteLibrary(Long libraryID) {
        LibraryEntity library = libraryRepository.findById(libraryID)
            .orElseThrow(() -> new RuntimeException("Library not found with id: " + libraryID));
        libraryRepository.delete(library);
    }
}
