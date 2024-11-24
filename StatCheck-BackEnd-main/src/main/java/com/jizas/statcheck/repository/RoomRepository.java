package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    // You can add custom query methods here if needed
}
