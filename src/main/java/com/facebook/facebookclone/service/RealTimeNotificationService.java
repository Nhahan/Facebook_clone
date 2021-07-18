package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.RealTimeNotificationRequestDto;
import com.facebook.facebookclone.model.*;
import com.facebook.facebookclone.repository.RealTimeNotificationRepository;
import com.facebook.facebookclone.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RealTimeNotificationService {

    private final RealTimeNotificationRepository realTimeNotificationRepository;
    private final UserProfileRepository userProfileRepository;

    public void generateNotification_articleLikeIt(RealTimeArticleNotification articleNotification) {
        String notification = articleNotification.getTeller() + "님이 " + articleNotification.getUsername() + "님의 게시글에 좋아요를 눌렀습니다.";
        realTimeNotificationRepository.save(new RealTimeArticleNotification(
                articleNotification.getUsername(),
                articleNotification.getTeller(),
                articleNotification.getArticleId(),
                notification)
        );
    }

    public void generateNotification_commentLikeIt(RealTimeCommentNotification commentNotification) {
        String notification = commentNotification.getTeller() + "님이 " + commentNotification.getUsername() + "님의 댓글에 좋아요를 눌렀습니다.";
        realTimeNotificationRepository.save(new RealTimeCommentNotification(
                commentNotification.getUsername(),
                commentNotification.getTeller(),
                commentNotification.getCommentId(),
                notification)
        );
    }

    public void generateNotification_recommentLikeIt(RealTimeRecommentNotification recommentNotification) {
        String notification = recommentNotification.getTeller() + "님이 " + recommentNotification.getUsername() + "님의 댓글에 좋아요를 눌렀습니다.";
        realTimeNotificationRepository.save(new RealTimeRecommentNotification(
                recommentNotification.getUsername(),
                recommentNotification.getTeller(),
                recommentNotification.getRecommentId(),
                notification)
        );
    }

    public void generateAdminNotification(RealTimeNotificationRequestDto requestDto) {
        if (requestDto.getUsername().equals("**")) {
            userProfileRepository.findAll().forEach(s -> realTimeNotificationRepository.save(new RealTimeNotification(s.getUsername(),
                    requestDto.getTeller(),
                    requestDto.getNotification())));
        } else {
            RealTimeNotification realTimeNotification = new RealTimeNotification(requestDto.getUsername(), requestDto.getTeller(), requestDto.getNotification());
            realTimeNotificationRepository.save(realTimeNotification);
        }
    }

    public List<RealTimeNotification> sendNotification() {
        return realTimeNotificationRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<RealTimeNotification> sendSpecificNotification(String username) {
        return realTimeNotificationRepository.findAllByUsernameByOrderByCreatedAtDesc(username);
    }
}
