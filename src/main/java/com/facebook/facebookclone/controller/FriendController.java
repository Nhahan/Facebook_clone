package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.FriendRequestDto;
import com.facebook.facebookclone.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/user/addFriend") // 친구 추가
    public void addFriend(@RequestBody FriendRequestDto requestDto) {
        friendService.addFriend(requestDto);
    }

    @GetMapping("/user/friends/{username}") // 친구 목록
    public Map<String, List<String>> getMyFriendsList(@PathVariable String username) {
        return friendService.myFriendsList(username);
    }

    @DeleteMapping("/user/deleteFriend") // 친구 삭제
    public void deleteFriend(@RequestBody FriendRequestDto requestDto) {
        friendService.deleteFriend(requestDto);
    }

    @GetMapping("/user/friendsRecommend") // 친추 추천 목록
    public Map<String, List<String>> getFriendsRecommned() {
        return friendService.getFriendsRecommend();
    }
}

