package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.entity.ParkingSpaceEntity;
import com.jizas.statcheck.repository.ParkingSpaceRepository;
import com.jizas.statcheck.repository.ParkingLotRepository;
import com.jizas.statcheck.dto.ParkingSpaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingSpaceDTO updateParkingSpace(Long id, String status, String spaceType) {
        ParkingSpaceEntity existingSpace = parkingSpaceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parking space not found"));
        
        existingSpace.setSpaceType(spaceType);
        existingSpace.setStatus(status);
        
        ParkingSpaceEntity updatedSpace = parkingSpaceRepository.save(existingSpace);
        return new ParkingSpaceDTO(updatedSpace);
    }

    public void deleteParkingSpace(Long id) {
        Optional<ParkingSpaceEntity> spaceOpt = parkingSpaceRepository.findById(id);
        if (spaceOpt.isPresent()) {
            ParkingSpaceEntity space = spaceOpt.get();
            ParkingLotEntity parkingLot = space.getParkingLot();
            
            if (parkingLot != null) {
                parkingLot.setSpaces(parkingLot.getSpaces() - 1);
                parkingLotRepository.save(parkingLot);
            }
            
            parkingSpaceRepository.deleteById(id);
        }
    }

    public ParkingSpaceDTO convertToDTO(ParkingSpaceEntity entity) {
        ParkingSpaceDTO dto = new ParkingSpaceDTO();
        dto.setParkingSpaceId(entity.getParkingSpaceId());
        dto.setParkingName(entity.getParkingName());
        dto.setStatus(entity.getStatus());
        dto.setSpaceType(entity.getSpaceType());
        if (entity.getParkingLot() != null) {
            dto.setParkingLotId(entity.getParkingLot().getParkingLotID());
        }
        return dto;
    }

    public ParkingSpaceEntity convertToEntity(ParkingSpaceDTO dto) {
        ParkingSpaceEntity entity = new ParkingSpaceEntity();
        entity.setParkingSpaceId(dto.getParkingSpaceId());
        entity.setParkingName(dto.getParkingName());
        entity.setStatus(dto.getStatus());
        entity.setSpaceType(dto.getSpaceType());
        if (dto.getParkingLotId() != null) {
            ParkingLotEntity parkingLot = new ParkingLotEntity();
            parkingLot.setParkingLotID(dto.getParkingLotId());
            entity.setParkingLot(parkingLot);
        }
        return entity;
    }
}
