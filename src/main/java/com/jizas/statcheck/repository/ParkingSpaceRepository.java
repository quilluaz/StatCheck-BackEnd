package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.ParkingSpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpaceEntity, Long> {
}
