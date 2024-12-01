package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.entity.ParkingReservationEntity;
import com.jizas.statcheck.entity.ParkingSpaceEntity;
import com.jizas.statcheck.entity.UserEntity;
import com.jizas.statcheck.repository.ParkingReservationRepository;
import com.jizas.statcheck.repository.ParkingSpaceRepository;
import com.jizas.statcheck.repository.UserRepository;
import com.jizas.statcheck.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParkingReservationService {

    @Autowired
    private ParkingReservationRepository parkingReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Transactional
    public ParkingReservationEntity createReservation(ParkingReservationEntity reservation) {
        // Debug logging
        System.out.println("Received reservation data: " + reservation);
        
        if (reservation.getParkingSpaceEntity() == null || 
            reservation.getParkingSpaceEntity().getParkingSpaceId() == null) {
            throw new RuntimeException("Parking space is required");
        }
        
        if (reservation.getUserEntity() == null || 
            reservation.getUserEntity().getUserID() == null) {
            throw new RuntimeException("User is required");
        }
        
        // Fetch and validate parking space
        ParkingSpaceEntity parkingSpace = parkingSpaceRepository.findById(
            reservation.getParkingSpaceEntity().getParkingSpaceId())
            .orElseThrow(() -> new RuntimeException("Parking space not found"));
                
        // Fetch and validate user
        UserEntity user = userRepository.findById(
            reservation.getUserEntity().getUserID())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the entities
        reservation.setParkingSpaceEntity(parkingSpace);
        reservation.setUserEntity(user);
        reservation.setParkingLotEntity(parkingSpace.getParkingLot());
        
        // Update parking space status
        parkingSpace.setStatus("RESERVED");
        parkingSpaceRepository.save(parkingSpace);
        
        // Save and return the reservation
        return parkingReservationRepository.save(reservation);
    }

    public List<ParkingReservationEntity> getAllReservations() {
        return parkingReservationRepository.findAll();
    }

    public ParkingReservationEntity getReservationById(Long id) {
        return parkingReservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    @Transactional
    public ParkingReservationEntity updateReservation(Long id, ParkingReservationEntity updatedReservation) {
        ParkingReservationEntity existingReservation = parkingReservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        UserEntity user = userRepository.findById(updatedReservation.getUserEntity().getUserID())
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        ParkingSpaceEntity parkingSpace = parkingSpaceRepository.findById(updatedReservation.getParkingSpaceEntity().getParkingSpaceId())
            .orElseThrow(() -> new RuntimeException("Parking space not found"));

        // If parking space is changing, update statuses
        if (!existingReservation.getParkingSpaceEntity().getParkingSpaceId().equals(parkingSpace.getParkingSpaceId())) {
            existingReservation.getParkingSpaceEntity().setStatus("available");
            parkingSpace.setStatus("reserved");
            parkingSpaceRepository.save(parkingSpace);
        }

        existingReservation.setUserEntity(user);
        existingReservation.setParkingSpaceEntity(parkingSpace);
        existingReservation.setStartTime(updatedReservation.getStartTime());
        existingReservation.setEndTime(updatedReservation.getEndTime());
        existingReservation.setStatus(updatedReservation.getStatus());

        return parkingReservationRepository.save(existingReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        try {
            ParkingReservationEntity reservation = parkingReservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

            // Update parking space status back to available
            ParkingSpaceEntity parkingSpace = reservation.getParkingSpaceEntity();
            if (parkingSpace != null) {
                parkingSpace.setStatus("AVAILABLE");
                parkingSpaceRepository.save(parkingSpace);
            }

            parkingReservationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting reservation: " + e.getMessage());
        }
    }
}
