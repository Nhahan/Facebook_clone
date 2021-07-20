package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.TestComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCommentRepository extends JpaRepository<TestComment, Long> {
}
