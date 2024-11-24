package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.ParkingReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingReservationRepository extends JpaRepository<ParkingReservationEntity, Long> {

    // Custom query to find expired reservations
    @Query("SELECT r FROM ParkingReservationEntity r WHERE r.endTime < :now AND r.reservationStatus = 'active'")
    List<ParkingReservationEntity> findExpiredReservations(LocalDateTime now);
}
