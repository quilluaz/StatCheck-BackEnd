package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findByBuilding_BldgID(Long buildingId);
}