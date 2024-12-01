package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @Autowired
    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingLotEntity>> getAllParkingLots() {
        List<ParkingLotEntity> parkingLots = parkingLotService.getAllParkingLots();
        return new ResponseEntity<>(parkingLots, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotEntity> getParkingLotById(@PathVariable("id") Long parkingLotID) {
        Optional<ParkingLotEntity> parkingLot = parkingLotService.getParkingLotById(parkingLotID);
        return parkingLot.map(lot -> new ResponseEntity<>(lot, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ParkingLotEntity> createParkingLot(@Valid @RequestBody ParkingLotEntity parkingLotEntity) {
        ParkingLotEntity savedLot = parkingLotService.saveParkingLot(parkingLotEntity);
        return new ResponseEntity<>(savedLot, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingLotEntity> updateParkingLot(@PathVariable("id") Long parkingLotID, @Valid @RequestBody ParkingLotEntity parkingLotEntity) {
        ParkingLotEntity updatedLot = parkingLotService.updateParkingLot(parkingLotID, parkingLotEntity);
        if (updatedLot != null) {
            return new ResponseEntity<>(updatedLot, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable("id") Long parkingLotID) {
        parkingLotService.deleteParkingLot(parkingLotID);
        return ResponseEntity.noContent().build();
    }
}
