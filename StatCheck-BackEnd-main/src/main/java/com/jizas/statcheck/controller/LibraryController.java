package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.LibraryEntity;
import com.jizas.statcheck.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // Endpoint to create a library and auto-generate rooms
    @PostMapping
    public LibraryEntity createLibrary(@RequestBody LibraryEntity libraryEntity) {
        return libraryService.createLibrary(libraryEntity);
    }

    // Endpoint to get all libraries
    @GetMapping
    public List<LibraryEntity> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    // Endpoint to get a specific library by its ID
    @GetMapping("/{libraryID}")
    public LibraryEntity getLibraryById(@PathVariable Long libraryID) {
        return libraryService.getLibraryById(libraryID);
    }
}
