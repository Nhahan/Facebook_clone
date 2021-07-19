package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.FriendRequestRequestDto;
import com.facebook.facebookclone.model.FriendRequest;
import com.facebook.facebookclone.repository.FriendRequestRepository;
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
    private final FriendRequestRepository friendRequestRepository;

    @PostMapping("/user/accept-friend") // 친구 추가
    public void addFriend(@RequestBody FriendRequestRequestDto requestDto) {
        friendService.acceptFriend(requestDto);
    }

    @GetMapping("/user/friends/{username}") // 친구 목록
    public Map<String, List<FriendObjectMappingFromUserProfile>> getMyFriendsList(@PathVariable String username) {
        return friendService.myFriendsList(username);
    }

    @DeleteMapping("/user/delete-friend/{username}/{friendName}") // 친구 삭제
    public void deleteFriend(@PathVariable String username, @PathVariable String friendName) {
        friendService.deleteFriend(username, friendName);
        friendService.deleteFriend(friendName, username);
    }

    @GetMapping("/user/friends-recommend") // 친추 추천 목록
    public Map<String, List<FriendObjectMappingFromUserProfile>> getFriendsRecommend() {
        return friendService.getFriendsRecommend();
    }

    // 친구 신청 API 목록
    @PostMapping("/user/request-friend") // 친구 신청
    public Map<String, String> requestFriend(@RequestBody FriendRequestRequestDto requestDto) {
        return friendService.requestFriend(requestDto);
    }

    @GetMapping("/user/request-friend-list/{friendName}") // 친구 신청 목록 확인
    public List<FriendRequest> getRequestFriendList(@PathVariable String friendName) {
        return friendService.getRequestFriendList(friendName);
    }

    @GetMapping("/user/request-friend/{username}/{friendName}") // 친구 신청 여부 확인
    public Map<String, Boolean> requestFriend(@PathVariable String username, @PathVariable String friendName) {
        return friendService.requestFriendChecker(username, friendName);
    }

    @DeleteMapping("/user/decline-friend/{username}/{friendName}")
    public void declineFriend(@PathVariable String username, @PathVariable String friendName) {
        friendService.declineFriend(username, friendName);
    }
}

