package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.TimeSlotEntity;
import com.jizas.statcheck.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    // Get all time slots
    @GetMapping
    public List<TimeSlotEntity> getAllTimeSlots() {
        return timeSlotService.getAllTimeSlots();
    }

    // Get a specific time slot by ID
    @GetMapping("/{timeSlotID}")
    public Optional<TimeSlotEntity> getTimeSlotById(@PathVariable Long timeSlotID) {
        return timeSlotService.getTimeSlotById(timeSlotID);
    }

    // Create a new time slot
    @PostMapping("/create")
    public TimeSlotEntity createTimeSlot(@RequestBody TimeSlotEntity timeSlotEntity) {
        return timeSlotService.createTimeSlot(timeSlotEntity);
    }

    @PutMapping("/{timeSlotID}")
    public TimeSlotEntity updateTimeSlot(@PathVariable Long timeSlotID, @RequestBody TimeSlotEntity timeSlotEntity) {
        return timeSlotService.updateTimeSlot(timeSlotID, timeSlotEntity);
    }

    @DeleteMapping("/{timeSlotID}")
    public void deleteTimeSlot(@PathVariable Long timeSlotID) {
        timeSlotService.deleteTimeSlot(timeSlotID);
    }
}
