package com.jizas.statcheck.controller;

import com.jizas.statcheck.entity.ScheduleEntity;
import com.jizas.statcheck.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleEntity>> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleEntity> getScheduleById(@PathVariable("id") Long scheduleId) {
        Optional<ScheduleEntity> schedule = scheduleService.getScheduleById(scheduleId);
        return schedule.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ScheduleEntity> createSchedule(@RequestBody ScheduleEntity scheduleEntity) {
        ScheduleEntity savedSchedule = scheduleService.saveSchedule(scheduleEntity);
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleEntity> updateSchedule(
            @PathVariable("id") Long scheduleId,
            @RequestBody ScheduleEntity scheduleEntity) {
        ScheduleEntity updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleEntity);
        if (updatedSchedule != null) {
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("id") Long scheduleId) {
        Optional<ScheduleEntity> existingSchedule = scheduleService.getScheduleById(scheduleId);
        if (existingSchedule.isPresent()) {
            scheduleService.deleteSchedule(scheduleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ScheduleEntity>> getSchedulesByRoom(@PathVariable Long roomId) {
        List<ScheduleEntity> schedules = scheduleService.findByRoomEntity_RoomId(roomId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<ScheduleEntity>> getSchedulesByDay(@PathVariable String dayOfWeek) {
        List<ScheduleEntity> schedules = scheduleService.findByDayOfWeek(dayOfWeek);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
}
