package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByOrderByCreatedAtAsc(Pageable pageable);
    Page<Article> findAllByUsernameOrderByCreatedAtAsc(String Username, Pageable pageable);
    Optional<Article> findById(Long id);
}
