package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.BuildingEntity;
import com.jizas.statcheck.entity.RoomEntity;
import com.jizas.statcheck.repository.BuildingRepository;
import com.jizas.statcheck.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<BuildingEntity> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Optional<BuildingEntity> getBuildingById(int bldgID) {
        return buildingRepository.findById(bldgID);
    }

    public BuildingEntity saveBuilding(BuildingEntity buildingEntity) {
        return buildingRepository.save(buildingEntity);
    }

    public void deleteBuilding(int bldgID) {
        buildingRepository.deleteById(bldgID);
    }

    // New method for updating the building
    public BuildingEntity updateBuilding(int bldgID, BuildingEntity buildingEntity) {
        Optional<BuildingEntity> existingBuilding = buildingRepository.findById(bldgID);
        if (existingBuilding.isPresent()) {
            BuildingEntity updatedBuilding = existingBuilding.get();
            updatedBuilding.setBuildingID(buildingEntity.getBuildingID());
            updatedBuilding.setFloors(buildingEntity.getFloors());
            return buildingRepository.save(updatedBuilding);
        }
        return null;
    }

    // New method to calculate total occupants in the building
    public int calculateTotalOccupants(int buildingID) {
        // Get the building by ID
        Optional<BuildingEntity> buildingOpt = buildingRepository.findById(buildingID);
        if (buildingOpt.isPresent()) {
            BuildingEntity building = buildingOpt.get();
            int totalOccupants = 0;

            // Sum the currentOccupancy of all rooms in the building
            List<RoomEntity> rooms = building.getRooms();
            for (RoomEntity room : rooms) {
                totalOccupants += room.getCurrentCapacity();
            }

            return totalOccupants;
        }

        // Return 0 if the building does not exist
        return 0;
    }
}
