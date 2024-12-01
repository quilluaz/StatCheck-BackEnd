package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.AnalyticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<AnalyticsEntity, Long> {
    List<AnalyticsEntity> findByRoomRoomId(Long roomId);
} 