package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.ArticleLikeItRequestDto;
import com.facebook.facebookclone.model.ArticleLikeIt;
import com.facebook.facebookclone.repository.ArticleLikeItRepository;
import com.facebook.facebookclone.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ArticleLikeItService {

    private final ArticleLikeItRepository articleLikeItRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Map<String, Boolean> articleLikeIt(ArticleLikeItRequestDto requestDto) {
        Map<String, Boolean> articleLikeItMap = new HashMap<>();

        if (articleRepository.findById(requestDto.getArticleId()).isPresent()) { // 게시글이 존재하면
            if (articleLikeItRepository.findByUsernameAndArticleId(requestDto.getUsername(), requestDto.getArticleId()).isPresent()) { // 좋아요가 이미 되어있으면
                articleLikeItRepository.deleteByUsernameAndArticleId(requestDto.getUsername(), requestDto.getArticleId());
                articleLikeItMap.put("articleLikeIt", false);
            } else {
                articleLikeItRepository.save(new ArticleLikeIt(requestDto));
                articleLikeItMap.put("articleLikeIt", true);
            }
        } else {
            throw new NullPointerException("Id가 " + requestDto.getArticleId() + "인 게시글이 존재하지 않습니다");
        }
        return articleLikeItMap;
    }
}
