package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.ArticleLikeIt;
import com.facebook.facebookclone.repository.mapping.ArticleMemberMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleLikeItRepository extends JpaRepository<ArticleLikeIt, Long> {
    Long countByArticleId(Long articleId);
    void deleteByUsernameAndArticleId(String username, Long articleId);
    ArticleLikeIt findByUsernameAndArticleId(String username, Long articleId);
    List<ArticleMemberMapping> findAllByArticleId(Long articleId);
}
