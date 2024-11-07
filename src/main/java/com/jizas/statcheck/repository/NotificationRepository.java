package com.jizas.statcheck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jizas.statcheck.entity.NotificationEntity;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

    // Find all notifications by a specific user ID
    List<NotificationEntity> findByUserId(int userId);

    // Find all unread notifications for a specific user
    List<NotificationEntity> findByUserIdAndStatus(int userId, int status);
}
