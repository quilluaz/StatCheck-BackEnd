package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.ParkingLotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingLotRepo extends JpaRepository<ParkingLotEntity, Long> {

    Optional<ParkingLotEntity> findByLotNumber(String lotNumber);

}
