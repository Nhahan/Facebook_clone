package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByIdDesc();
    Long countByArticleId(Long articleId);
}
