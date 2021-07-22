package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.CommentLikeItRequestDto;
import com.facebook.facebookclone.model.CommentLikeIt;
import com.facebook.facebookclone.repository.CommentLikeItRepository;
import com.facebook.facebookclone.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CommentLikeItService {

    private final CommentLikeItRepository commentLikeItRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Map<String, Boolean> commentILikeIt(CommentLikeItRequestDto requestDto) {
        Map<String, Boolean> commentLikeItMap = new HashMap<>();
        if (commentRepository.findById(requestDto.getCommentId()).isPresent()) {
            if (commentLikeItRepository.findByUsernameAndCommentId(requestDto.getUsername(), requestDto.getCommentId()).isPresent()) {
                commentLikeItRepository.deleteByUsernameAndCommentId(requestDto.getUsername(), requestDto.getCommentId());
                commentLikeItMap.put("commentLikeIt", false);
            } else {
                commentLikeItRepository.save(new CommentLikeIt(requestDto));
                commentLikeItMap.put("commentLikeIt", true);
            }
        } else {
            throw new NullPointerException("Id가 " + requestDto.getCommentId() + "인 댓글이 존재하지 않습니다");
        }
        return commentLikeItMap;
    }
}
