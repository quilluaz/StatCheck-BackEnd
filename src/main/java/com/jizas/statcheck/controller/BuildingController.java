package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.BuildingEntity;
import com.jizas.statcheck.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@CrossOrigin(origins = "http://localhost:3000")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public List<BuildingEntity> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

    @GetMapping("/{id}")
    public BuildingEntity getBuilding(@PathVariable Long id) {
        return buildingService.getBuilding(id);
    }

    @PostMapping
    public BuildingEntity createBuilding(@RequestBody BuildingEntity building) {
        return buildingService.saveBuilding(building);
    }

    @PutMapping("/{id}")
    public BuildingEntity updateBuilding(@PathVariable Long id, @RequestBody BuildingEntity building) {
        building.setBldgID(id);
        return buildingService.saveBuilding(building);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.ok().build();
    }
}