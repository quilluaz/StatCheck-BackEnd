package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.ParkingReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkingReservationRepo extends JpaRepository<ParkingReservationEntity, Long> {
    
    // Find reservations by user ID
    List<ParkingReservationEntity> findByUserId(Long userId);

    // Find reservations by status
    List<ParkingReservationEntity> findByReservationStatus(String reservationStatus);

    // Find reservations within a specific date range
    List<ParkingReservationEntity> findByReservationStartTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
