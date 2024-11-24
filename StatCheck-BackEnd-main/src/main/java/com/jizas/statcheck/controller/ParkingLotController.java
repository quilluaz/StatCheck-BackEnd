package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.service.ParkingLotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping
    public List<ParkingLotEntity> getAllParkingLots() {
        return parkingLotService.getAllParkingLots();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotEntity> getParkingLotById(@PathVariable Long id) {
        return ResponseEntity.of(parkingLotService.getParkingLotById(id));
    }

    @PostMapping
    public ParkingLotEntity createParkingLot(@RequestBody ParkingLotEntity parkingLot) {
        return parkingLotService.saveParkingLot(parkingLot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingLotEntity> updateParkingLot(
            @PathVariable Long id,
            @RequestBody ParkingLotEntity parkingLotDetails) {
        return ResponseEntity.of(parkingLotService.updateParkingLot(id, parkingLotDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long id) {
        if (parkingLotService.deleteParkingLot(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
