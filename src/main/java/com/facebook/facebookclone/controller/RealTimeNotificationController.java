package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.RealTimeNotificationRequestDto;
import com.facebook.facebookclone.model.RealTimeNotification;
import com.facebook.facebookclone.repository.RealTimeNotificationRepository;
import com.facebook.facebookclone.service.RealTimeNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RealTimeNotificationController {

    private final RealTimeNotificationService realTimeNotificationService;
    private final RealTimeNotificationRepository realTimeNotificationRepository;

    @GetMapping("/user/notification")
    public List<RealTimeNotification> sendNotification() {
        return realTimeNotificationService.sendNotification();
    }

    @GetMapping("/user/notification/{username}")
    public List<RealTimeNotification> sendSpecificNotification(@PathVariable String username) {
        return realTimeNotificationService.sendSpecificNotification(username);
    }

    @DeleteMapping("/user/notification/{notificationId}")
    public void deleteNotification(@PathVariable Long notificationId) {
        realTimeNotificationRepository.deleteById(notificationId);
    }

    @PostMapping("/user/notification") // 운영자 알림 등록
    public void generateFullNotification(@RequestBody RealTimeNotificationRequestDto requestDto) {
        realTimeNotificationService.generateAdminNotification(requestDto);
    }
}
