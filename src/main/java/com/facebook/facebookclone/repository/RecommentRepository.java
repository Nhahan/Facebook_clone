package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    List<Recomment> findAllByOrderByIdDesc();

    Long countByCommentId(Long recommentId);
}
