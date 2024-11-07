
package com.jizas.statcheck.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jizas.statcheck.entity.TimeSlotEntity;

import com.jizas.statcheck.service.TimeSlotService;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/timeslots")
public class TimeSlotController {
    @Autowired
    private TimeSlotService timeSlotService;

    @PostMapping("/create")
    public TimeSlotEntity createTimeSlot(@RequestBody TimeSlotEntity timeSlot) {
        return timeSlotService.createTimeSlot(timeSlot);
    }

    @GetMapping("/getAll")
    public List<TimeSlotEntity> getAllTimeSlots() {
        return timeSlotService.getAllTimeSlots();
    }

    @GetMapping("/getById/{timeSlotId}")
    public TimeSlotEntity getTimeSlotById(@PathVariable int timeSlotId) {
        return timeSlotService.getTimeSlotById(timeSlotId);
    }

    @PutMapping("/update/{timeSlotId}")
    public TimeSlotEntity updateTimeSlot(@PathVariable int timeSlotId, @RequestBody TimeSlotEntity timeSlot) {
        return timeSlotService.updateTimeSlot(timeSlotId, timeSlot);
    }

    @DeleteMapping("/deleteById/{timeSlotId}")
    public void deleteTimeSlotById(@PathVariable int timeSlotId) {
        timeSlotService.deleteTimeSlotById(timeSlotId);
    }
}
