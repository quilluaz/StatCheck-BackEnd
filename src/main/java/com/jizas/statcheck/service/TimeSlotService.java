
package com.jizas.statcheck.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jizas.statcheck.entity.ScheduleEntity;
import com.jizas.statcheck.entity.SubjectEntity;
import com.jizas.statcheck.entity.TimeSlotEntity;
import com.jizas.statcheck.repository.ScheduleRepository;
import com.jizas.statcheck.repository.SubjectRepository;
import com.jizas.statcheck.repository.TimeSlotRepository;

@Service
public class TimeSlotService {
    @Autowired
    private TimeSlotRepository timeSlotRepository;


    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SubjectRepository subjectRepository; 
    public TimeSlotEntity createTimeSlot(TimeSlotEntity timeSlot) {
        // Ensure schedule and subject are provided
        if (timeSlot.getSchedule() == null || timeSlot.getSubject() == null) {
            throw new RuntimeException("Schedule and Subject must be provided");
        }
    
        // Fetch the ScheduleEntity based on the provided scheduleId
        ScheduleEntity schedule = scheduleRepository.findById(timeSlot.getSchedule().getScheduleId())
            .orElseThrow(() -> new RuntimeException("Schedule not found"));
    
        // Set the fetched schedule to the timeSlot
        timeSlot.setSchedule(schedule);
    
        // Fetch the SubjectEntity based on the provided subjectId
        SubjectEntity subject = subjectRepository.findById(timeSlot.getSubject().getSubjectId())
            .orElseThrow(() -> new RuntimeException("Subject not found"));
    
        // Set the fetched subject to the timeSlot
        timeSlot.setSubject(subject);
        
        // Save the time slot with the associated schedule and subject
        return timeSlotRepository.save(timeSlot);
    }
    
    
    public List<TimeSlotEntity> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public TimeSlotEntity getTimeSlotById(int timeSlotId) {
        return timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new NoSuchElementException("TimeSlot not found with ID: " + timeSlotId));
    }

    public TimeSlotEntity updateTimeSlot(int timeSlotId, TimeSlotEntity updatedTimeSlot) {
        TimeSlotEntity existingTimeSlot = getTimeSlotById(timeSlotId);
        existingTimeSlot.setStartTime(updatedTimeSlot.getStartTime());
        existingTimeSlot.setEndTime(updatedTimeSlot.getEndTime());
        existingTimeSlot.setSchedule(updatedTimeSlot.getSchedule());
        existingTimeSlot.setSubject(updatedTimeSlot.getSubject()); 
        return timeSlotRepository.save(existingTimeSlot);
    }

    public void deleteTimeSlotById(int timeSlotId) {
        timeSlotRepository.deleteById(timeSlotId);
    }
}

