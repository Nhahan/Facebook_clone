package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.FriendRequestDto;
import com.facebook.facebookclone.repository.FriendRepository;
import com.facebook.facebookclone.repository.mapping.FriendMapping;
import com.facebook.facebookclone.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;
    private final FriendRepository friendRepository;

    @PostMapping("/user/addfriend")
    public void addFriend(@RequestBody FriendRequestDto requestDto) {
        friendService.addFriend(requestDto);
    }

    @GetMapping("/user/friends/{username}")
    public List<FriendMapping> getMyFriendsList(@PathVariable String username) {
        return friendRepository.findAllByUsername(username);
    }
}

