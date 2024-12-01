package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.LibraryEntity;
import com.jizas.statcheck.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public List<LibraryEntity> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    @GetMapping("/{libraryID}")
    public LibraryEntity getLibraryById(@PathVariable Long libraryID) {
        return libraryService.getLibraryById(libraryID);
    }

    @PostMapping
    public LibraryEntity createLibrary(@RequestBody LibraryEntity libraryEntity) {
        return libraryService.createLibrary(libraryEntity);
    }

    @PutMapping("/{libraryID}")
    public LibraryEntity updateLibrary(@PathVariable Long libraryID, @RequestBody LibraryEntity libraryEntity) {
        return libraryService.updateLibrary(libraryID, libraryEntity);
    }

    @DeleteMapping("/{libraryID}")
    public void deleteLibrary(@PathVariable Long libraryID) {
        libraryService.deleteLibrary(libraryID);
    }
}