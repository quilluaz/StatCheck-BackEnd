package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.LibraryRoomEntity;
import com.jizas.statcheck.entity.LibraryRoomReservationEntity;
import com.jizas.statcheck.entity.UserEntity;
import com.jizas.statcheck.repository.LibraryRoomRepository;
import com.jizas.statcheck.repository.LibraryRoomReservationRepository;
import com.jizas.statcheck.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryRoomReservationService {

    @Autowired
    private LibraryRoomReservationRepository libraryRoomReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibraryRoomRepository libraryRoomRepository;

    @Transactional
    public LibraryRoomReservationEntity createReservation(LibraryRoomReservationEntity reservation) {
        // Fetch and set the actual entities
        UserEntity user = userRepository.findById(reservation.getUserEntity().getUserID())
            .orElseThrow(() -> new RuntimeException("User not found"));
        LibraryRoomEntity room = libraryRoomRepository.findById(reservation.getLibraryRoomEntity().getLibraryRoomID())
            .orElseThrow(() -> new RuntimeException("Library room not found"));

        reservation.setUserEntity(user);
        reservation.setLibraryRoomEntity(room);

        return libraryRoomReservationRepository.save(reservation);
    }

    // Method to check if the room is available
    private boolean isRoomAvailable(LibraryRoomEntity room, LocalDateTime startTime, LocalDateTime endTime) {
        // For simplicity, checking if the room's current status is "Available"
        return room.getStatus().equals("Available");
    }

    // Method to retrieve all room reservations
    public List<LibraryRoomReservationEntity> getAllReservations() {
        return libraryRoomReservationRepository.findAll();
    }

    // Method to retrieve a specific room reservation by ID
    public LibraryRoomReservationEntity getReservationById(Long id) {
        Optional<LibraryRoomReservationEntity> reservation = libraryRoomReservationRepository.findById(id);
        return reservation.orElse(null);  // Returns null if the reservation doesn't exist
    }

    @Transactional
    public LibraryRoomReservationEntity updateReservation(Long id, LibraryRoomReservationEntity updatedReservation) {
        LibraryRoomReservationEntity existingReservation = libraryRoomReservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        // Fetch and set the actual entities
        UserEntity user = userRepository.findById(updatedReservation.getUserEntity().getUserID())
            .orElseThrow(() -> new RuntimeException("User not found"));
        LibraryRoomEntity room = libraryRoomRepository.findById(updatedReservation.getLibraryRoomEntity().getLibraryRoomID())
            .orElseThrow(() -> new RuntimeException("Library room not found"));

        existingReservation.setUserEntity(user);
        existingReservation.setLibraryRoomEntity(room);
        existingReservation.setStartTime(updatedReservation.getStartTime());
        existingReservation.setEndTime(updatedReservation.getEndTime());
        existingReservation.setStatus(updatedReservation.getStatus());

        return libraryRoomReservationRepository.save(existingReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        if (!libraryRoomReservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
        libraryRoomReservationRepository.deleteById(id);
    }
}
