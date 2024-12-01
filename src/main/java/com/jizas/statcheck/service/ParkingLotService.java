package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.entity.ParkingSpaceEntity;
import com.jizas.statcheck.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public List<ParkingLotEntity> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }

    public Optional<ParkingLotEntity> getParkingLotById(Long parkingLotID) {
        return parkingLotRepository.findById(parkingLotID);
    }

    @Transactional
    public ParkingLotEntity saveParkingLot(ParkingLotEntity parkingLotEntity) {
        // Create new parking lot
        ParkingLotEntity newLot = new ParkingLotEntity();
        newLot.setParkingLotName(parkingLotEntity.getParkingLotName());
        newLot.setSpaces(parkingLotEntity.getSpaces());
        
        // Initialize parking spaces
        List<ParkingSpaceEntity> spaces = new ArrayList<>();
        for (int i = 1; i <= parkingLotEntity.getSpaces(); i++) {
            ParkingSpaceEntity space = new ParkingSpaceEntity(
                "Space " + i,
                "AVAILABLE",
                i % 2 == 0 ? "CAR" : "MOTORCYCLE",
                newLot
            );
            spaces.add(space);
        }
        newLot.setParkingSpaces(spaces);
        
        // Save the parking lot with its spaces
        return parkingLotRepository.save(newLot);
    }

    @Transactional
    public ParkingLotEntity updateParkingLot(Long parkingLotID, ParkingLotEntity parkingLotEntity) {
        Optional<ParkingLotEntity> existingLot = parkingLotRepository.findById(parkingLotID);
        if (existingLot.isPresent()) {
            ParkingLotEntity updatedLot = existingLot.get();
            updatedLot.setParkingLotName(parkingLotEntity.getParkingLotName());
            
            // Only update spaces if the number has changed
            if (updatedLot.getSpaces() != parkingLotEntity.getSpaces()) {
                updatedLot.setSpaces(parkingLotEntity.getSpaces());
                
                // Create new spaces if increasing the number
                int currentSpaces = updatedLot.getParkingSpaces().size();
                if (parkingLotEntity.getSpaces() > currentSpaces) {
                    for (int i = currentSpaces + 1; i <= parkingLotEntity.getSpaces(); i++) {
                        ParkingSpaceEntity space = new ParkingSpaceEntity(
                            "Space " + i,
                            "AVAILABLE",
                            i % 2 == 0 ? "CAR" : "MOTORCYCLE",
                            updatedLot
                        );
                        updatedLot.getParkingSpaces().add(space);
                    }
                } else if (parkingLotEntity.getSpaces() < currentSpaces) {
                    // Remove excess spaces if decreasing the number
                    List<ParkingSpaceEntity> spacesToKeep = new ArrayList<>(
                        updatedLot.getParkingSpaces().subList(0, parkingLotEntity.getSpaces())
                    );
                    updatedLot.getParkingSpaces().clear();
                    updatedLot.getParkingSpaces().addAll(spacesToKeep);
                }
            }
            
            return parkingLotRepository.save(updatedLot);
        }
        return null;
    }

    public void deleteParkingLot(Long parkingLotID) {
        parkingLotRepository.deleteById(parkingLotID);
    }

    // Calculate total available spaces in a parking lot
    public int calculateAvailableSpaces(Long parkingLotID) {
        Optional<ParkingLotEntity> parkingLotOpt = parkingLotRepository.findById(parkingLotID);
        if (parkingLotOpt.isPresent()) {
            ParkingLotEntity parkingLot = parkingLotOpt.get();
            return (int) parkingLot.getParkingSpaces().stream()
                    .filter(space -> "AVAILABLE".equals(space.getStatus()))
                    .count();
        }
        return 0;
    }
}
