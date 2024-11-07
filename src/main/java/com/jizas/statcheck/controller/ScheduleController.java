
package com.jizas.statcheck.controller;

import java.util.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jizas.statcheck.entity.ScheduleEntity;

import com.jizas.statcheck.service.ScheduleService;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(method = RequestMethod.GET,path="/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleEntity createSchedule(@RequestBody ScheduleEntity schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @GetMapping("/getAll")
    public List<ScheduleEntity> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/getById/{scheduleId}")
    public ScheduleEntity getScheduleById(@PathVariable int scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @PutMapping("/update/{scheduleId}")
    public ScheduleEntity updateSchedule(@PathVariable int scheduleId, @RequestBody ScheduleEntity schedule) {
        return scheduleService.updateSchedule(scheduleId, schedule);
    }

    @DeleteMapping("/deleteById/{scheduleId}")
    public void deleteScheduleById(@PathVariable int scheduleId) {
        scheduleService.deleteScheduleById(scheduleId);
    }
//checker
    @PostMapping("/json")
    public ResponseEntity<String> testJson(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok("Received: " + request.toString());
    }
    
}