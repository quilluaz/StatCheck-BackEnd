package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ParkingReservationEntity;
import com.jizas.statcheck.service.ParkingReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ParkingReservationController {

    @Autowired
    private ParkingReservationService parkingReservationService;

    // Create a new reservation
    @PostMapping
    public ResponseEntity<ParkingReservationEntity> createReservation(@RequestBody ParkingReservationEntity reservation) {
        reservation.setUserId(null);  // Set user ID to null explicitly
        ParkingReservationEntity createdReservation = parkingReservationService.createReservation(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    // Retrieve a reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingReservationEntity> getReservationById(@PathVariable Long id) {
        return parkingReservationService.getReservationById(id)
                .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Retrieve all reservations
    @GetMapping
    public ResponseEntity<List<ParkingReservationEntity>> getAllReservations() {
        List<ParkingReservationEntity> reservations = parkingReservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Update an existing reservation
    @PutMapping("/{id}")
    public ResponseEntity<ParkingReservationEntity> updateReservation(@PathVariable Long id,
                                                                     @RequestBody ParkingReservationEntity reservationDetails) {
        try {
            ParkingReservationEntity updatedReservation = parkingReservationService.updateReservation(id, reservationDetails);
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a reservation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            parkingReservationService.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
