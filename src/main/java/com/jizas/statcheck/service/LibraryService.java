package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.LibraryEntity;
import com.jizas.statcheck.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    // Create a new library and auto-generate rooms
    public LibraryEntity createLibrary(LibraryEntity libraryEntity) {
        // Logic to create a library and auto-generate rooms if necessary
        return libraryRepository.save(libraryEntity);
    }

    // Get all libraries
    public List<LibraryEntity> getAllLibraries() {
        return libraryRepository.findAll();
    }

    // Get a specific library by ID
    public LibraryEntity getLibraryById(Long libraryID) {
        Optional<LibraryEntity> library = libraryRepository.findById(libraryID);
        return library.orElse(null);
    }
}
