package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.LibraryRoomReservationEntity;
import com.jizas.statcheck.service.LibraryRoomReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/library-reservations")
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

    @PutMapping("/{id}")
    public ResponseEntity<LibraryRoomReservationEntity> updateReservation(
            @PathVariable Long id,
            @RequestBody LibraryRoomReservationEntity reservation) {
        try {
            LibraryRoomReservationEntity updatedReservation = 
                libraryRoomReservationService.updateReservation(id, reservation);
            return ResponseEntity.ok(updatedReservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            libraryRoomReservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
