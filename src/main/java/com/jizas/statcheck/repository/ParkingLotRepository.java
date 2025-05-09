package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.ParkingLotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLotEntity, Long> {
    boolean existsByParkingLotName(String parkingLotName);
}
