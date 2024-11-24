package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ParkingSpaceEntity;
import com.jizas.statcheck.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    public List<ParkingSpaceEntity> getAllParkingSpaces() {
        return parkingSpaceRepository.findAll();
    }

    public Optional<ParkingSpaceEntity> getParkingSpaceById(Long id) {
        return parkingSpaceRepository.findById(id);
    }

    public ParkingSpaceEntity saveParkingSpace(ParkingSpaceEntity parkingSpace) {
        parkingSpace.updateStatus();  // Ensure status is updated before saving
        return parkingSpaceRepository.save(parkingSpace);
    }

    public Optional<ParkingSpaceEntity> updateParkingSpace(Long id, ParkingSpaceEntity parkingSpaceDetails) {
        return parkingSpaceRepository.findById(id).map(existingSpace -> {
            existingSpace.setParkingName(parkingSpaceDetails.getParkingName());
            existingSpace.setSpaceType(parkingSpaceDetails.getSpaceType()); // Update space type as well
            existingSpace.updateStatus(); // Update the status
            return parkingSpaceRepository.save(existingSpace);
        });
    }

    public boolean deleteParkingSpace(Long id) {
        if (parkingSpaceRepository.existsById(id)) {
            parkingSpaceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
