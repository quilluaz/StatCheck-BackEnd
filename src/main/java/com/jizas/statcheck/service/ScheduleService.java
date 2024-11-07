
package com.jizas.statcheck.service;

import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.jizas.statcheck.entity.ScheduleEntity;
import com.jizas.statcheck.repository.ScheduleRepository;

@Service
public class ScheduleService {
    public ScheduleService(){
        super();
    }

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ScheduleEntity createSchedule(ScheduleEntity schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleEntity> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public ScheduleEntity getScheduleById(int scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoSuchElementException("Schedule not found with ID: " + scheduleId));
    }

    

    public ScheduleEntity updateSchedule(int scheduleId, ScheduleEntity updatedSchedule) {
        ScheduleEntity existingSchedule = getScheduleById(scheduleId);
        existingSchedule.setRoomID(updatedSchedule.getRoomID());
        existingSchedule.setDayOfWeek(updatedSchedule.getDayOfWeek());
        return scheduleRepository.save(existingSchedule);
    }

    public void deleteScheduleById(int scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }


}

