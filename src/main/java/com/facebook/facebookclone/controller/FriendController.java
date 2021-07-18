package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.FriendRequestDto;
import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import com.facebook.facebookclone.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/user/add-friend") // 친구 추가
    public void addFriend(@RequestBody FriendRequestDto requestDto) {
        friendService.addFriend(requestDto);
    }

    @GetMapping("/user/friends/{username}") // 친구 목록
    public Map<String, List<FriendObjectMappingFromUserProfile>> getMyFriendsList(@PathVariable String username) {
        return friendService.myFriendsList(username);
    }

    @DeleteMapping("/user/delete-friend/{username}/{friendName}") // 친구 삭제
    public void deleteFriend(@PathVariable String username, @PathVariable String friendName) {
        friendService.deleteFriend(username, friendName);
    }

    @GetMapping("/user/friends-recommend") // 친추 추천 목록
    public Map<String, List<FriendObjectMappingFromUserProfile>> getFriendsRecommend() {
        return friendService.getFriendsRecommend();
    }
}

