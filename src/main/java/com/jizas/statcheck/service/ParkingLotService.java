package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.repository.ParkingLotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepo parkingLotRepo;

    // Create a new parking lot
    public ParkingLotEntity createParkingLot(ParkingLotEntity parkingLot) {
        if (parkingLot.getAvailableSpaces() == null || parkingLot.getAvailableSpaces().isEmpty()) {
            parkingLot.setAvailableSpaces(initializeAvailableSpaces(parkingLot.getLotNumber(), parkingLot.getTotalSpaces()));
        }
        return parkingLotRepo.save(parkingLot);
    }

    // Retrieve a parking lot by ID
    public ParkingLotEntity getParkingLotById(Long id) {
        ParkingLotEntity parkingLot = parkingLotRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Lot not found"));

        if (parkingLot.getAvailableSpaces() == null || parkingLot.getAvailableSpaces().isEmpty()) {
            parkingLot.setAvailableSpaces(initializeAvailableSpaces(parkingLot.getLotNumber(), parkingLot.getTotalSpaces()));
            parkingLotRepo.save(parkingLot);
        }

        return parkingLot;
    }

    // Retrieve all parking lots
    public List<ParkingLotEntity> getAllParkingLots() {
        return parkingLotRepo.findAll();
    }

    // Update an existing parking lot
    public ParkingLotEntity updateParkingLot(Long id, ParkingLotEntity parkingLotDetails) {
        ParkingLotEntity existingParkingLot = parkingLotRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Lot not found"));

        existingParkingLot.setLotNumber(parkingLotDetails.getLotNumber());
        existingParkingLot.setTotalSpaces(parkingLotDetails.getTotalSpaces());

        existingParkingLot.setAvailableSpaces(initializeAvailableSpaces(existingParkingLot.getLotNumber(), existingParkingLot.getTotalSpaces()));

        return parkingLotRepo.save(existingParkingLot);
    }

    // Delete a parking lot by ID
    public void deleteParkingLot(Long id) {
        parkingLotRepo.deleteById(id);
    }

    // Helper method to initialize available spaces
    private List<String> initializeAvailableSpaces(String lotNumber, int totalSpaces) {
        List<String> spaces = new ArrayList<>();
        for (int i = 1; i <= totalSpaces; i++) {
            spaces.add(lotNumber + i);
        }
        return spaces;
    }
}
