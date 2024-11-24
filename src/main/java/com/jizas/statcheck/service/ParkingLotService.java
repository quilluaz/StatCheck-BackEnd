package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    // Fetch all parking lots from the database
    public List<ParkingLotEntity> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }

    // Fetch a parking lot by its ID
    public Optional<ParkingLotEntity> getParkingLotById(Long id) {
        return parkingLotRepository.findById(id);
    }

    // Save a new parking lot or update an existing one
    public ParkingLotEntity saveParkingLot(ParkingLotEntity parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    // Update a parking lot's details based on the given ID
    public Optional<ParkingLotEntity> updateParkingLot(Long id, ParkingLotEntity parkingLotDetails) {
        return parkingLotRepository.findById(id).map(existingLot -> {
            existingLot.setParkingLotName(parkingLotDetails.getParkingLotName());
            existingLot.setSpaces(parkingLotDetails.getSpaces());
            return parkingLotRepository.save(existingLot);
        });
    }

    // Delete a parking lot by its ID
    public boolean deleteParkingLot(Long id) {
        if (parkingLotRepository.existsById(id)) {
            parkingLotRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Optional: A method to check if a parking lot with a specific name already exists
    public boolean isParkingLotNameExist(String parkingLotName) {
        return parkingLotRepository.existsByParkingLotName(parkingLotName);
    }

    // Optional: A method to create parking lots dynamically based on the space count
    public ParkingLotEntity createParkingLotWithSpaces(String parkingLotName, int spaces) {
        ParkingLotEntity newParkingLot = new ParkingLotEntity(parkingLotName, spaces);
        return parkingLotRepository.save(newParkingLot);
    }
}
