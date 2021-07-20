package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    Long countByCommentId(Long recommentId);
    void deleteByUsernameAndCommentId(String username, Long commentId);
    void deleteByCommentId(Long commentId);
    List<Recomment> findAllByUsernameAndCommentId(String username, Long commentId);
}
