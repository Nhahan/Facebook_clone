package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.TestArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestArticleRepository extends JpaRepository<TestArticle, Long> {
}
