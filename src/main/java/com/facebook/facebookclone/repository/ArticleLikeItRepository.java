package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.ArticleLikeIt;
import com.facebook.facebookclone.repository.mapping.UsernameMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleLikeItRepository extends JpaRepository<ArticleLikeIt, Long> {
    Long countByArticleId(Long articleId);
    void deleteByUsernameAndArticleId(String username, Long articleId);
    Optional<ArticleLikeIt> findByUsernameAndArticleId(String username, Long articleId);
    List<UsernameMapping> findAllByArticleId(Long articleId);
}
