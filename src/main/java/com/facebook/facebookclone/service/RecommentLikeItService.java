package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.RecommentLikeItRequestDto;
import com.facebook.facebookclone.model.*;
import com.facebook.facebookclone.repository.RecommentLikeItRepository;
import com.facebook.facebookclone.repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecommentLikeItService {

    private final RecommentLikeItRepository recommentLikeItRepository;
    private final RecommentRepository recommentRepository;

    @Transactional
    public Map<String, Boolean> recommentILikeIt(RecommentLikeItRequestDto requestDto) {
        Map<String, Boolean> recommentLikeItMap = new HashMap<>();
        if (recommentRepository.findById(requestDto.getRecommentId()).isPresent()) {
            Recomment recomment = recommentRepository.findById(requestDto.getRecommentId()).get();
            Optional<RecommentLikeIt> recommentLikeSize = Optional.ofNullable(recommentLikeItRepository.findByUsernameAndRecommentId(requestDto.getUsername(), requestDto.getRecommentId()));
            if (recommentLikeSize.isPresent()) {
                recommentLikeItRepository.deleteByUsernameAndRecommentId(requestDto.getUsername(), requestDto.getRecommentId());
                recommentLikeItMap.put("commentLikeIt", false);
            } else {
                recommentLikeItRepository.save(new RecommentLikeIt(requestDto));
                recommentLikeItMap.put("commentLikeIt", true);
            }

//            // generateNotification
//            String recipient = recomment.getUsername(); // 알림 받을 username
//            String teller = requestDto.getUsername(); // 알림 주는 username
//            Long commentIdOnNotification = requestDto.getRecommentId(); // 알림 게시글Id
//            if (!recipient.equals(teller)) { // 자기가 자기꺼 누르면 알림 생성하지 않음
//                realTimeNotificationService.generateNotification_recommentLikeIt(new RealTimeRecommentNotification(recipient, teller, commentIdOnNotification));
//            }
//            //

        } else {
            throw new NullPointerException("Id가 " + requestDto.getRecommentId() + "인 댓글이 존재하지 않습니다");
        }
        return recommentLikeItMap;
    }
}
