package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ScheduleEntity;
import com.jizas.statcheck.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleEntity> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{scheduleID}")
    public Optional<ScheduleEntity> getScheduleById(@PathVariable Long scheduleID) {
        return scheduleService.getScheduleById(scheduleID);
    }

    @PostMapping
    public ScheduleEntity createSchedule(@RequestBody ScheduleEntity scheduleEntity) {
        return scheduleService.createSchedule(scheduleEntity);
    }

    @PutMapping("/{scheduleID}")
    public ScheduleEntity updateSchedule(@PathVariable Long scheduleID, @RequestBody ScheduleEntity scheduleEntity) {
        return scheduleService.updateSchedule(scheduleID, scheduleEntity);
    }

    @DeleteMapping("{scheduleID}")
    public void deleteSchedule(@PathVariable Long scheduleID) {
        scheduleService.deleteSchedule(scheduleID);
    }
}
