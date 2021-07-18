package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.RealTimeNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealTimeNotificationRepository extends JpaRepository<RealTimeNotification, Long> {
    List<RealTimeNotification> findAllByOrderByCreatedAtDesc();
    List<RealTimeNotification> findAllByUsernameByOrderByCreatedAtDesc(String username);
}
