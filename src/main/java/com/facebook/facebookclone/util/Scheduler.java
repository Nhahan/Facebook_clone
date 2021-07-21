package com.facebook.facebookclone.util;

import com.facebook.facebookclone.model.FriendRequest;
import com.facebook.facebookclone.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class Scheduler {

    private final FriendRequestRepository friendRequestRepository;

//    @Scheduled(cron = "0 0 0 * * *") // 초, 분, 시, 일, 월, 주 순서
//    public void tempScheduler() {
//    }

    @Scheduled(cron = "0 0 0/3 * * *") // 3시간마다 실행
    public void AutoDeleteThreeHourly() {
        // 오래된 친구 요청 삭제
        for (FriendRequest request : friendRequestRepository.findAll()) {
            int createdTime = Integer.parseInt(request.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            int nowTime = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            if (createdTime - nowTime >= 7) {
                friendRequestRepository.delete(request);
            }
        }
        // 오래된 알림 삭제
//        for (RealTimeNotification notification : realTimeNotificationRepository.findAll()) {
//            int createdTime = Integer.parseInt(notification.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//            int nowTime = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//            if (createdTime - nowTime >= 2) {
//                realTimeNotificationRepository.delete(notification);
//            }
//        }
    }
}
