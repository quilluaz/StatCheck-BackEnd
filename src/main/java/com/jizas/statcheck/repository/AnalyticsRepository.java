package com.jizas.statcheck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jizas.statcheck.entity.AnalyticsEntity;

@Repository
public interface AnalyticsRepository extends JpaRepository<AnalyticsEntity, Integer> {

    // Custom method to find analytics by RoomID
    List<AnalyticsEntity> findByRoomId(int roomId);
}