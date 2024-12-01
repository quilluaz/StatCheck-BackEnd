package com.jizas.statcheck.repository;

import com.jizas.statcheck.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByDayOfWeek(String dayOfWeek);
    List<ScheduleEntity> findByRoomEntity_RoomId(Long roomId);
}
