package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ParkingReservationEntity;
import com.jizas.statcheck.entity.ParkingLotEntity;
import com.jizas.statcheck.repository.ParkingReservationRepo;
import com.jizas.statcheck.repository.ParkingLotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingReservationService {

    @Autowired
    private ParkingReservationRepo parkingReservationRepo;

    @Autowired
    private ParkingLotRepo parkingLotRepository;

    // Create a new reservation
    public ParkingReservationEntity createReservation(ParkingReservationEntity reservation) {
        ParkingLotEntity parkingLot = reservation.getParkingLot();

        ParkingLotEntity existingParkingLot = parkingLotRepository.findById(parkingLot.getLotId())
                .orElseThrow(() -> new RuntimeException("Parking Lot not found"));

        List<String> availableSpaces = existingParkingLot.getAvailableSpaces();
        if (availableSpaces == null || availableSpaces.isEmpty()) {
            throw new RuntimeException("No available spaces");
        }

        String assignedSpace = availableSpaces.get(0);
        reservation.setSpaceNumber(assignedSpace);
        reservation.setReservationStatus("Confirmed");

        availableSpaces.remove(0);
        existingParkingLot.setAvailableSpaces(availableSpaces);
        parkingLotRepository.save(existingParkingLot);

        reservation.setParkingLot(existingParkingLot);  // Attach fully populated ParkingLotEntity
        return parkingReservationRepo.save(reservation);
    }

    // Retrieve a reservation by ID with a fully populated ParkingLotEntity
    public Optional<ParkingReservationEntity> getReservationById(Long id) {
        Optional<ParkingReservationEntity> reservation = parkingReservationRepo.findById(id);
        reservation.ifPresent(res -> {
            ParkingLotEntity parkingLot = parkingLotRepository.findById(res.getParkingLot().getLotId())
                    .orElseThrow(() -> new RuntimeException("Parking Lot not found"));
            res.setParkingLot(parkingLot);  // Ensure fully populated ParkingLotEntity is attached
        });
        return reservation;
    }

    // Retrieve all reservations
    public List<ParkingReservationEntity> getAllReservations() {
        return parkingReservationRepo.findAll();
    }

    // Update an existing reservation
    public ParkingReservationEntity updateReservation(Long id, ParkingReservationEntity reservationDetails) {
        ParkingReservationEntity existingReservation = parkingReservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        existingReservation.setReservationStartTime(reservationDetails.getReservationStartTime());
        existingReservation.setReservationEndTime(reservationDetails.getReservationEndTime());

        return parkingReservationRepo.save(existingReservation);
    }

    // Delete a reservation by ID
    public void deleteReservation(Long id) {
        parkingReservationRepo.deleteById(id);
    }
}
