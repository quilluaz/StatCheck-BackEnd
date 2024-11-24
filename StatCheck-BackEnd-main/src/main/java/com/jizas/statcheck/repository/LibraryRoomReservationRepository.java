package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.LibraryRoomReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRoomReservationRepository extends JpaRepository<LibraryRoomReservationEntity, Long> {
    // Custom queries can be added here if needed
}
