package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    // Create a new parking lot
    @PostMapping
    public ResponseEntity<ParkingLotEntity> createParkingLot(@RequestBody ParkingLotEntity parkingLot) {
        ParkingLotEntity createdParkingLot = parkingLotService.createParkingLot(parkingLot);
        return new ResponseEntity<>(createdParkingLot, HttpStatus.CREATED);
    }

    // Retrieve a parking lot by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotEntity> getParkingLotById(@PathVariable Long id) {
        try {
            ParkingLotEntity parkingLot = parkingLotService.getParkingLotById(id);
            return new ResponseEntity<>(parkingLot, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Retrieve all parking lots
    @GetMapping
    public ResponseEntity<List<ParkingLotEntity>> getAllParkingLots() {
        List<ParkingLotEntity> parkingLots = parkingLotService.getAllParkingLots();
        return new ResponseEntity<>(parkingLots, HttpStatus.OK);
    }

    // Update an existing parking lot
    @PutMapping("/{id}")
    public ResponseEntity<ParkingLotEntity> updateParkingLot(@PathVariable Long id, @RequestBody ParkingLotEntity parkingLotDetails) {
        try {
            ParkingLotEntity updatedParkingLot = parkingLotService.updateParkingLot(id, parkingLotDetails);
            return new ResponseEntity<>(updatedParkingLot, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a parking lot by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long id) {
        try {
            parkingLotService.deleteParkingLot(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
