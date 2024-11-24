package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ScheduleEntity;
import com.jizas.statcheck.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    // Get all schedules
    public List<ScheduleEntity> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Get schedule by ID
    public Optional<ScheduleEntity> getScheduleById(Long scheduleID) {
        return scheduleRepository.findById(scheduleID);
    }

    // Create a new schedule
    public ScheduleEntity createSchedule(ScheduleEntity scheduleEntity) {
        return scheduleRepository.save(scheduleEntity);
    }

    // Update an existing schedule
    public ScheduleEntity updateSchedule(Long scheduleID, ScheduleEntity scheduleEntity) {
        Optional<ScheduleEntity> existingSchedule = scheduleRepository.findById(scheduleID);
        if (existingSchedule.isPresent()) {
            ScheduleEntity updatedSchedule = existingSchedule.get();
            updatedSchedule.setDayOfWeek(scheduleEntity.getDayOfWeek());
            return scheduleRepository.save(updatedSchedule);
        }
        return null;
    }

    // Delete a schedule
    public void deleteSchedule(Long scheduleID) {
        scheduleRepository.deleteById(scheduleID);
    }
}
