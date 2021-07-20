package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.FriendRequestRequestDto;
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

    @PostMapping("/user/accept-friend") // 친구 신청 승낙
    public void acceptFriend(@RequestBody FriendRequestRequestDto requestDto) {
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

    @GetMapping("/user/friends-recommend/{username}") // 친추 추천 목록
    public Map<String, List<FriendObjectMappingFromUserProfile>> getFriendsRecommend(@PathVariable String username) {
        return friendService.getFriendsRecommend(username);
    }

    // 친구 신청 API 목록
    @PostMapping("/user/request-friend") // 친구 신청
    public Map<String, String> requestFriend(@RequestBody FriendRequestRequestDto requestDto) {
        return friendService.requestFriend(requestDto);
    }

    @GetMapping("/user/request-friend-list/given/{username}") // (신청한 사람 기준) 친구 신청 목록 확인
    public List<FriendObjectMappingFromUserProfile> getGivenRequestFriendList(@PathVariable String username) {
        return friendService.getGivenRequestFriendList(username);
    }

    @GetMapping("/user/request-friend-list/received/{username}") // (신청 받은 사람 기준) 친구 신청 목록 확인
    public List<FriendObjectMappingFromUserProfile> getReceivedRequestFriendList(@PathVariable String username) {
        return friendService.getReceivedRequestFriendList(username);
    }

    @DeleteMapping("/user/decline-friend/given/{username}/{friendName}") // (신청한 사람 기준) 친구 신청 거절
    public void declineGivenFriend(@PathVariable String username, @PathVariable String friendName) {
        friendService.declineGivenFriend(username, friendName);
    }

    @DeleteMapping("/user/decline-friend/received/{username}/{friendName}") // (신청 받은 사람 기준) 친구 신청 거절
    public void declineReceivedFriend(@PathVariable String username, @PathVariable String friendName) {
        friendService.declineReceivedFriend(username, friendName);
    }

    @GetMapping("/user/request-friend/{username}/{friendName}") // 친구 신청 여부 확인
    public Map<String, Boolean> requestFriend(@PathVariable String username, @PathVariable String friendName) {
        return friendService.requestFriendChecker(username, friendName);
    }
}

