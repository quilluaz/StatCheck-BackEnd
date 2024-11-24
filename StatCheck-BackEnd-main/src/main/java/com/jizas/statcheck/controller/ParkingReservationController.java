package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ParkingReservationEntity;
import com.jizas.statcheck.service.ParkingReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parking-reservations")
public class    ParkingReservationController {

    private final ParkingReservationService parkingReservationService;

    @Autowired
    public ParkingReservationController(ParkingReservationService parkingReservationService) {
        this.parkingReservationService = parkingReservationService;
    }

    @PostMapping
    public ResponseEntity<ParkingReservationEntity> createReservation(@RequestBody ParkingReservationEntity parkingReservationEntity) {
        if (parkingReservationEntity.getParkingSpace() == null) {
            return ResponseEntity.badRequest().body(null);  // If parkingSpace is null, return a bad request response
        }
        ParkingReservationEntity createdReservation = parkingReservationService.createReservation(parkingReservationEntity);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    // Get all reservations
    @GetMapping
    public List<ParkingReservationEntity> getAllReservations() {
        return parkingReservationService.getAllReservations();
    }

    // Get a specific reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingReservationEntity> getReservationById(@PathVariable Long id) {
        Optional<ParkingReservationEntity> reservation = parkingReservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing reservation
    @PutMapping("/{id}")
    public ResponseEntity<ParkingReservationEntity> updateReservation(@PathVariable Long id, @RequestBody ParkingReservationEntity updatedReservation) {
        try {
            ParkingReservationEntity reservation = parkingReservationService.updateReservation(id, updatedReservation);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        parkingReservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
