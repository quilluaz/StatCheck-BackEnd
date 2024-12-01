package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.ParkingReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingReservationRepository extends JpaRepository<ParkingReservationEntity, Long> {
    // Custom queries can be added here if needed
}
