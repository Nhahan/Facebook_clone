package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.RecommentLikeIt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommentLikeItRepository extends JpaRepository<RecommentLikeIt, Long> {
    Long countByRecommentId(Long articleId);

    void deleteByUsernameAndRecommentId(String username, Long recommentId);

    RecommentLikeIt findByUsernameAndRecommentId(String username, Long recommentId);
}
