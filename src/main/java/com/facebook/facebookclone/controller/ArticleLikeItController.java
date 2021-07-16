package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.ArticleLikeItRequestDto;
import com.facebook.facebookclone.model.ArticleLikeIt;
import com.facebook.facebookclone.repository.ArticleLikeItRepository;
import com.facebook.facebookclone.service.ArticleLikeItService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ArticleLikeItController {

    private final ArticleLikeItService articleLikeItService;
    private final ArticleLikeItRepository articleLikeItRepository;

    @PostMapping("/user/article/likeIt") // 좋아요!
    public Map<String, Boolean> clickLike(@RequestBody ArticleLikeItRequestDto requestDto) {
        return articleLikeItService.articleLikeIt(requestDto);
    }

    @GetMapping("/user/article/likeIt") // 좋아요 보기
    public List<ArticleLikeIt> likeItCheck() {
        return articleLikeItRepository.findAll();
    }
}
