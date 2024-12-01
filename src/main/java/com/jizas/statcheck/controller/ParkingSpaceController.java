package com.jizas.statcheck.controller;

import com.jizas.statcheck.dto.ParkingSpaceDTO;
import com.jizas.statcheck.service.ParkingSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/parking-spaces")
public class ParkingSpaceController {

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpaceDTO> updateParkingSpace(
            @PathVariable Long id,
            @RequestBody Map<String, String> updates) {
        try {
            ParkingSpaceDTO updatedSpace = parkingSpaceService.updateParkingSpace(
                id,
                updates.get("status"),
                updates.get("spaceType")
            );
            return ResponseEntity.ok(updatedSpace);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable Long id) {
        try {
            parkingSpaceService.deleteParkingSpace(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
