package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.FriendRequestDto;
import com.facebook.facebookclone.model.Friend;
import com.facebook.facebookclone.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;

    public void addFriend(FriendRequestDto requestDto) {
        Friend friend = new Friend(requestDto);
        friendRepository.save(friend);
    }
}
