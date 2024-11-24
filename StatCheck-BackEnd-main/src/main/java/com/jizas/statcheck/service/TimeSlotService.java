package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.TimeSlotEntity;
import com.jizas.statcheck.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    // Get all time slots
    public List<TimeSlotEntity> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    // Get time slot by ID
    public Optional<TimeSlotEntity> getTimeSlotById(Long timeSlotID) {
        return timeSlotRepository.findById(timeSlotID);
    }

    // Create a new time slot
    public TimeSlotEntity createTimeSlot(TimeSlotEntity timeSlotEntity) {
        return timeSlotRepository.save(timeSlotEntity);
    }

    // Update an existing time slot
    public TimeSlotEntity updateTimeSlot(Long timeSlotID, TimeSlotEntity timeSlotEntity) {
        Optional<TimeSlotEntity> existingTimeSlot = timeSlotRepository.findById(timeSlotID);
        if (existingTimeSlot.isPresent()) {
            TimeSlotEntity updatedTimeSlot = existingTimeSlot.get();
            updatedTimeSlot.setStartTime(timeSlotEntity.getStartTime());
            updatedTimeSlot.setEndTime(timeSlotEntity.getEndTime());
            return timeSlotRepository.save(updatedTimeSlot);
        }
        return null;
    }

    // Delete a time slot
    public void deleteTimeSlot(Long timeSlotID) {
        timeSlotRepository.deleteById(timeSlotID);
    }
}
