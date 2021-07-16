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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleLikeItService {

    private final ArticleLikeItRepository articleLikeItRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Map<String, Boolean> articleLikeIt(ArticleLikeItRequestDto requestDto) {
        Map<String, Boolean> articleLikeItMap = new HashMap<>();
        if (articleRepository.findById(requestDto.getArticleId()).isPresent()) {
            Optional<ArticleLikeIt> articleLikeSize = Optional.ofNullable(articleLikeItRepository.findByUsernameAndArticleId(requestDto.getUsername(), requestDto.getArticleId()));
            if (articleLikeSize.isPresent()) {
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
