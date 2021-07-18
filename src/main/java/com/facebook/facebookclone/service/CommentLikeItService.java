package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.CommentLikeItRequestDto;
import com.facebook.facebookclone.model.Comment;
import com.facebook.facebookclone.model.CommentLikeIt;
import com.facebook.facebookclone.model.RealTimeCommentNotification;
import com.facebook.facebookclone.repository.CommentLikeItRepository;
import com.facebook.facebookclone.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentLikeItService {

    private final CommentLikeItRepository commentLikeItRepository;
    private final CommentRepository commentRepository;
    private final RealTimeNotificationService realTimeNotificationService;

    @Transactional
    public Map<String, Boolean> commentILikeIt(CommentLikeItRequestDto requestDto) {
        Map<String, Boolean> commentLikeItMap = new HashMap<>();
        if (commentRepository.findById(requestDto.getCommentId()).isPresent()) {
            Comment comment = commentRepository.findById(requestDto.getCommentId()).get();
            Optional<CommentLikeIt> commentLikeSize = Optional.ofNullable(commentLikeItRepository.findByUsernameAndCommentId(requestDto.getUsername(), requestDto.getCommentId()));
            if (commentLikeSize.isPresent()) {
                commentLikeItRepository.deleteByUsernameAndCommentId(requestDto.getUsername(), requestDto.getCommentId());
                commentLikeItMap.put("commentLikeIt", false);
            } else {
                commentLikeItRepository.save(new CommentLikeIt(requestDto));
                commentLikeItMap.put("commentLikeIt", true);
            }

            // generateNotification
            String recipient = comment.getUsername(); // 알림 받을 username
            String teller = requestDto.getUsername(); // 알림 주는 username
            Long commentIdOnNotification = requestDto.getCommentId(); // 알림 게시글Id
            if (!recipient.equals(teller)) { // 자기가 자기꺼 누르면 알림 생성하지 않음
                realTimeNotificationService.generateNotification_commentLikeIt(new RealTimeCommentNotification(recipient, teller, commentIdOnNotification));
            }
            //

        } else {
            throw new NullPointerException("Id가 " + requestDto.getCommentId() + "인 댓글이 존재하지 않습니다");
        }
        return commentLikeItMap;
    }
}
