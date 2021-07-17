package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.CommentLikeItRequestDto;
import com.facebook.facebookclone.model.CommentLikeIt;
import com.facebook.facebookclone.repository.CommentLikeItRepository;
import com.facebook.facebookclone.service.CommentLikeItService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentLikeItController {

    private final CommentLikeItRepository commentLikeItRepository;
    private final CommentLikeItService commentLikeItService;

    @PostMapping("/user/comment/likeIt") // 좋아요!
    public Map<String, Boolean> clickLike(@RequestBody CommentLikeItRequestDto requestDto) {
        return commentLikeItService.commentILikeIt(requestDto);
    }

    @GetMapping("/user/comment/likeIt") // 좋아요 보기
    public List<CommentLikeIt> readCommentLikes() {
        return commentLikeItRepository.findAll();
    }
}
