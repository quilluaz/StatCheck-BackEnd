
package com.jizas.statcheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jizas.statcheck.entity.TimeSlotEntity;

public interface TimeSlotRepository extends JpaRepository <TimeSlotEntity, Integer> {

}

