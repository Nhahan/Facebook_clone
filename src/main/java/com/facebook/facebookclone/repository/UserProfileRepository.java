package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.UserProfile;
import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
    FriendObjectMappingFromUserProfile getByUsername(String username);
    List<UserProfile> findAllByUsername(String username);
    List<FriendObjectMappingFromUserProfile> findAllByOrderByModifiedAtDesc();
    Page<FriendObjectMappingFromUserProfile> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<UserProfile> findAll();

    List<FriendObjectMappingFromUserProfile> findAllByUsernameContaining(String username);
}
