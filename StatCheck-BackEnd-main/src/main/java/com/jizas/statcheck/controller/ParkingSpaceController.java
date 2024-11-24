package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.entity.ParkingSpaceEntity;
import com.jizas.statcheck.service.ParkingSpaceService;
import com.jizas.statcheck.service.ParkingLotService; // Make sure this import is included
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;
    private final ParkingLotService parkingLotService; // Declare ParkingLotService

    // Update the constructor to include ParkingLotService
    public ParkingSpaceController(ParkingSpaceService parkingSpaceService, ParkingLotService parkingLotService) {
        this.parkingSpaceService = parkingSpaceService;
        this.parkingLotService = parkingLotService; // Inject ParkingLotService
    }

    @GetMapping
    public List<ParkingSpaceEntity> getAllParkingSpaces() {
        return parkingSpaceService.getAllParkingSpaces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpaceEntity> getParkingSpaceById(@PathVariable Long id) {
        return ResponseEntity.of(parkingSpaceService.getParkingSpaceById(id));
    }

    @PostMapping("/{parkingLotId}")
    public ResponseEntity<ParkingSpaceEntity> createParkingSpace(
            @PathVariable Long parkingLotId,
            @RequestBody ParkingSpaceEntity parkingSpace) {

        // Fetch the ParkingLotEntity by ID
        ParkingLotEntity parkingLot = parkingLotService.getParkingLotById(parkingLotId)
                .orElse(null);  // Return null if parking lot is not found

        if (parkingLot == null) {
            return ResponseEntity.notFound().build();  // If parking lot doesn't exist
        }

        // Associate the parking space with the parking lot
        parkingSpace.setParkingLot(parkingLot);

        // Save the parking space
        ParkingSpaceEntity savedParkingSpace = parkingSpaceService.saveParkingSpace(parkingSpace);
        return ResponseEntity.ok(savedParkingSpace);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpaceEntity> updateParkingSpace(
            @PathVariable Long id,
            @RequestBody ParkingSpaceEntity parkingSpaceDetails) {
        return ResponseEntity.of(parkingSpaceService.updateParkingSpace(id, parkingSpaceDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Long id) {
        if (parkingSpaceService.deleteParkingSpace(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
