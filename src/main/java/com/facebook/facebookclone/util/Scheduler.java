package com.facebook.facebookclone.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Scheduler {

    @Scheduled(cron = "0 0 0 * * *") // 초, 분, 시, 일, 월, 주 순서
    public void tempScheduler() {
    }
}
