package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ParkingReservationEntity;
import com.jizas.statcheck.service.ParkingReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/parking-reservations")
public class ParkingReservationController {

    @Autowired
    private ParkingReservationService parkingReservationService;

    @PostMapping
    public ParkingReservationEntity createReservation(@RequestBody ParkingReservationEntity reservation) {
        return parkingReservationService.createReservation(reservation);
    }

    @GetMapping
    public List<ParkingReservationEntity> getAllReservations() {
        return parkingReservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ParkingReservationEntity getReservationById(@PathVariable Long id) {
        return parkingReservationService.getReservationById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingReservationEntity> updateReservation(
            @PathVariable Long id,
            @RequestBody ParkingReservationEntity reservation) {
        try {
            ParkingReservationEntity updatedReservation = 
                parkingReservationService.updateReservation(id, reservation);
            return ResponseEntity.ok(updatedReservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            parkingReservationService.deleteReservation(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
