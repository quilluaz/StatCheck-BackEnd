package com.jizas.statcheck.service;

import com.jizas.statcheck.entity.ScheduleEntity;
import com.jizas.statcheck.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleEntity> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<ScheduleEntity> getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    public ScheduleEntity saveSchedule(ScheduleEntity scheduleEntity) {
        return scheduleRepository.save(scheduleEntity);
    }

    public ScheduleEntity updateSchedule(Long scheduleId, ScheduleEntity scheduleEntity) {
        Optional<ScheduleEntity> existingSchedule = scheduleRepository.findById(scheduleId);
        if (existingSchedule.isPresent()) {
            ScheduleEntity updatedSchedule = existingSchedule.get();
            updatedSchedule.setDayOfWeek(scheduleEntity.getDayOfWeek());
            updatedSchedule.setStartTime(scheduleEntity.getStartTime());
            updatedSchedule.setEndTime(scheduleEntity.getEndTime());
            updatedSchedule.setRoomEntity(scheduleEntity.getRoomEntity());
            updatedSchedule.setSubjectEntity(scheduleEntity.getSubjectEntity());
            return scheduleRepository.save(updatedSchedule);
        }
        return null;
    }

    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    public List<ScheduleEntity> findByDayOfWeek(String dayOfWeek) {
        return scheduleRepository.findByDayOfWeek(dayOfWeek);
    }

    public List<ScheduleEntity> findByRoomEntity_RoomId(Long roomId) {
        return scheduleRepository.findByRoomEntity_RoomId(roomId);
    }
}
