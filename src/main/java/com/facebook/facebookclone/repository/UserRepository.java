package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.User;
import com.facebook.facebookclone.repository.mapping.UsernameMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailAddress(String emailAddress);
    Long countAllByUsernameContaining(String username);
    Optional<User> findByKakaoId(Long kakaoId);
    Optional<Object> findTopByUsername(String username);

    Optional<UsernameMapping> findFirstByUsernameContaining(String username);
//    Page<UsernameMapping> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
