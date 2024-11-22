package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {
}
