package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.LibraryRoomEntity;
import com.jizas.statcheck.entity.LibraryRoomReservationEntity;
import com.jizas.statcheck.repository.LibraryRoomRepository;
import com.jizas.statcheck.repository.LibraryRoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryRoomReservationService {

    @Autowired
    private LibraryRoomReservationRepository libraryRoomReservationRepository;

    @Autowired
    private LibraryRoomRepository libraryRoomRepository;

    // Method to create a new room reservation and update the status of the room
    public LibraryRoomReservationEntity createReservation(LibraryRoomReservationEntity reservation) {
        // Check if reservation time overlaps with existing reservation
        Optional<LibraryRoomEntity> roomOpt = libraryRoomRepository.findById(reservation.getLibraryRoomEntity().getLibraryRoomID());
        if (roomOpt.isPresent()) {
            LibraryRoomEntity room = roomOpt.get();

            // Check if the time is within the reservation period
            LocalDateTime startTime = reservation.getStartTime();
            LocalDateTime endTime = reservation.getEndTime();

            // Set the status based on whether the room is reserved during the requested time
            if (isRoomAvailable(room, startTime, endTime)) {
                reservation.setStatus("Occupied");
                room.setStatus("Occupied");
            } else {
                reservation.setStatus("Available");
                room.setStatus("Available");
            }

            // Save the reservation and update the room status
            libraryRoomReservationRepository.save(reservation);
            libraryRoomRepository.save(room);
            return reservation;
        }
        return null;
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
}
