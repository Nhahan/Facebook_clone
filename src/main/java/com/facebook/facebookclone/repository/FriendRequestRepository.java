package com.facebook.facebookclone.repository;

import com.facebook.facebookclone.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findAllByUsernameAndFriendName(String username, String friendName);
    List<FriendRequest> findAllByFriendName(String friendName);
    void deleteByUsernameAndFriendName(String username, String friendName);
}
