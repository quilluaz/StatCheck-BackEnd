package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ParkingReservationEntity;
import com.jizas.statcheck.repository.ParkingReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingReservationService {

    private final ParkingReservationRepository parkingReservationRepository;

    @Autowired
    public ParkingReservationService(ParkingReservationRepository parkingReservationRepository) {
        this.parkingReservationRepository = parkingReservationRepository;
    }

    // Create a new reservation
    public ParkingReservationEntity createReservation(ParkingReservationEntity parkingReservationEntity) {
        return parkingReservationRepository.save(parkingReservationEntity);
    }

    // Get all reservations
    public List<ParkingReservationEntity> getAllReservations() {
        return parkingReservationRepository.findAll();
    }

    // Get a specific reservation by ID
    public Optional<ParkingReservationEntity> getReservationById(Long id) {
        return parkingReservationRepository.findById(id);
    }

    // Update an existing reservation
    public ParkingReservationEntity updateReservation(Long id, ParkingReservationEntity updatedReservation) {
        return parkingReservationRepository.findById(id).map(reservation -> {
            reservation.setStartTime(updatedReservation.getStartTime());
            reservation.setEndTime(updatedReservation.getEndTime());
            reservation.setReservationStatus(updatedReservation.getReservationStatus());
            return parkingReservationRepository.save(reservation);
        }).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    // Delete a reservation
    public void deleteReservation(Long id) {
        parkingReservationRepository.deleteById(id);
    }

    // Scheduled task to delete expired reservations
    @Scheduled(fixedRate = 60000)  // Run every minute
    public void deleteExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();  // Get current time
        List<ParkingReservationEntity> reservations = parkingReservationRepository.findAll();

        // Iterate through all reservations and delete expired ones
        for (ParkingReservationEntity reservation : reservations) {
            if (reservation.getEndTime().isBefore(now) && reservation.getReservationStatus().equals("active")) {
                parkingReservationRepository.delete(reservation);  // Delete expired reservation
                System.out.println("Expired reservation deleted: " + reservation.getReservationID());
            }
        }
    }
}
