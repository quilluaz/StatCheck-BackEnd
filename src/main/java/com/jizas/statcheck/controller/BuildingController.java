package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.BuildingEntity;
import com.jizas.statcheck.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public ResponseEntity<List<BuildingEntity>> getAllBuildings() {
        List<BuildingEntity> buildings = buildingService.getAllBuildings();
        return new ResponseEntity<>(buildings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingEntity> getBuildingById(@PathVariable("id") int bldgID) {
        Optional<BuildingEntity> building = buildingService.getBuildingById(bldgID);
        return building.map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BuildingEntity> createBuilding(@Valid @RequestBody BuildingEntity buildingEntity) {
        BuildingEntity savedBuilding = buildingService.saveBuilding(buildingEntity);
        return new ResponseEntity<>(savedBuilding, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildingEntity> updateBuilding(@PathVariable("id") int bldgID,
                                                         @Valid @RequestBody BuildingEntity buildingEntity) {
        BuildingEntity updatedBuilding = buildingService.updateBuilding(bldgID, buildingEntity);
        if (updatedBuilding != null) {
            return new ResponseEntity<>(updatedBuilding, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable("id") int bldgID) {
        buildingService.deleteBuilding(bldgID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{buildingID}/total-occupants")
    public int getTotalOccupants(@PathVariable int buildingID) {
        return buildingService.calculateTotalOccupants(buildingID);
    }
}

@RestController
@RequestMapping("/api/buildings/user")
class UserBuildingController {

    private final BuildingService buildingService;

    @Autowired
    public UserBuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    // Get all buildings (Read-only for user access)
    @GetMapping
    public ResponseEntity<List<BuildingEntity>> getAllBuildings() {
        List<BuildingEntity> buildings = buildingService.getAllBuildings();
        return ResponseEntity.ok(buildings);
    }

    // Get building by ID (Read-only for user access)
    @GetMapping("/{id}")
    public ResponseEntity<BuildingEntity> getBuildingById(@PathVariable("id") int bldgID) {
        Optional<BuildingEntity> building = buildingService.getBuildingById(bldgID);
        return building.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get total occupants for a building (Read-only for user access)
    @GetMapping("/{buildingID}/total-occupants")
    public ResponseEntity<Integer> getTotalOccupants(@PathVariable int buildingID) {
        int totalOccupants = buildingService.calculateTotalOccupants(buildingID);
        return ResponseEntity.ok(totalOccupants);
    }
}
