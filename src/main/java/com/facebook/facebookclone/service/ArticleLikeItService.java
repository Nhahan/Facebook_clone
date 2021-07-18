package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.ArticleLikeItRequestDto;
import com.facebook.facebookclone.model.Article;
import com.facebook.facebookclone.model.ArticleLikeIt;
import com.facebook.facebookclone.model.RealTimeArticleNotification;
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
    private final RealTimeNotificationService realTimeNotificationService;

    @Transactional
    public Map<String, Boolean> articleLikeIt(ArticleLikeItRequestDto requestDto) {
        Map<String, Boolean> articleLikeItMap = new HashMap<>();

        if (articleRepository.findById(requestDto.getArticleId()).isPresent()) { // 게시글이 존재하면
            Article article = articleRepository.findById(requestDto.getArticleId()).get();
            Optional<ArticleLikeIt> articleLikeIt = Optional.ofNullable(articleLikeItRepository.findByUsernameAndArticleId(requestDto.getUsername(), requestDto.getArticleId()));
            if (articleLikeIt.isPresent()) { // 좋아요가 이미 되어있으면
                articleLikeItRepository.deleteByUsernameAndArticleId(requestDto.getUsername(), requestDto.getArticleId());
                articleLikeItMap.put("articleLikeIt", false);
            } else {
                articleLikeItRepository.save(new ArticleLikeIt(requestDto));
                articleLikeItMap.put("articleLikeIt", true);
            }

            // generateNotification
            String recipient = article.getUsername(); // 알림 받을 username
            String teller = requestDto.getUsername(); // 알림 주는 username
            Long notificationArticleId = requestDto.getArticleId(); // 알림 게시글Id
            if (!recipient.equals(teller)) { // 자기가 자기꺼 누르면 알림 생성하지 않음
                realTimeNotificationService.generateNotification_articleLikeIt(new RealTimeArticleNotification(recipient, teller, notificationArticleId));
            }
            //

        } else {
            throw new NullPointerException("Id가 " + requestDto.getArticleId() + "인 게시글이 존재하지 않습니다");
        }
        return articleLikeItMap;
    }
}
