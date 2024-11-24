package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.LibraryRoomReservationEntity;
import com.jizas.statcheck.service.LibraryRoomReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-room-reservations")
public class LibraryRoomReservationController {

    @Autowired
    private LibraryRoomReservationService libraryRoomReservationService;

    // Endpoint to create a room reservation
    @PostMapping
    public LibraryRoomReservationEntity createReservation(@RequestBody LibraryRoomReservationEntity reservation) {
        return libraryRoomReservationService.createReservation(reservation);
    }

    // Endpoint to get all room reservations
    @GetMapping
    public List<LibraryRoomReservationEntity> getAllReservations() {
        return libraryRoomReservationService.getAllReservations();
    }

    // Endpoint to get a specific room reservation by ID
    @GetMapping("/{id}")
    public LibraryRoomReservationEntity getReservationById(@PathVariable Long id) {
        return libraryRoomReservationService.getReservationById(id);
    }
}
