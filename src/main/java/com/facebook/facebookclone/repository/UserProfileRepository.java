package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
    List<UserProfile> findAllByUsername(String username);
}
