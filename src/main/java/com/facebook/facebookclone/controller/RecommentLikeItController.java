package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.RecommentLikeItRequestDto;
import com.facebook.facebookclone.model.RecommentLikeIt;
import com.facebook.facebookclone.repository.RecommentLikeItRepository;
import com.facebook.facebookclone.service.RecommentLikeItService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class RecommentLikeItController {

    private final RecommentLikeItRepository recommentLikeItRepository;
    private final RecommentLikeItService recommentLikeItService;

    @PostMapping("/recommentLikeIt") // 좋아요!
    public Map<String, Boolean> clickLike(@RequestBody RecommentLikeItRequestDto requestDto) {
        return recommentLikeItService.recommentILikeIt(requestDto);
    }

    @GetMapping("/recommentLikeIt") // 좋아요 보기
    public List<RecommentLikeIt> readCommentLikes() {
        return recommentLikeItRepository.findAll();
    }
}
