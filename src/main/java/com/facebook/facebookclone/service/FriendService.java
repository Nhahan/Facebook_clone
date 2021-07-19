package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.FriendRequestRequestDto;
import com.facebook.facebookclone.model.Friend;
import com.facebook.facebookclone.model.FriendRequest;
import com.facebook.facebookclone.repository.FriendRepository;
import com.facebook.facebookclone.repository.FriendRequestRepository;
import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.repository.UserRepository;
import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void acceptFriend(FriendRequestRequestDto requestDto) {
        if (friendRequestRepository.findAllByUsernameAndFriendName(requestDto.getFriendName(), requestDto.getUsername()).isEmpty()) {
            throw new NullPointerException("친구 신청이 없습니다.");
        } else {
            if (friendRepository.findAllByUsernameAndFriendName(requestDto.getUsername(), requestDto.getFriendName()).isEmpty()) {
                friendRepository.save(new Friend(requestDto.getUsername(), requestDto.getFriendName()));
                if (friendRepository.findAllByUsernameAndFriendName(requestDto.getFriendName(), requestDto.getUsername()).isEmpty()) {
                    friendRepository.save(new Friend(requestDto.getFriendName(), requestDto.getUsername()));
                }
            friendRequestRepository.deleteByUsernameAndFriendName(requestDto.getUsername(), requestDto.getFriendName());
            friendRequestRepository.deleteByUsernameAndFriendName(requestDto.getFriendName(), requestDto.getUsername());
            }
        }
    }

    @Transactional
    public void deleteFriend(String username, String friendName) {
        if (userRepository.findByUsername(username).isPresent() && userRepository.findByUsername(friendName).isPresent()) {
            if (friendRepository.findAllByUsernameAndFriendName(username, friendName).isEmpty()) {
                throw new IllegalArgumentException(username + "&" + friendName + " -> 친구가 아닙니다.");
            } else {
                friendRepository.deleteAllByUsernameAndFriendName(username, friendName);
            }
        } else {
            throw new NullPointerException("존재하지 않는 유저입니다.");
        }
    }

    public Map<String, List<FriendObjectMappingFromUserProfile>> myFriendsList(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            Map<String, List<FriendObjectMappingFromUserProfile>> friendsListMap = new HashMap<>();
            List<String> friendsList = new ArrayList<>();
            List<FriendObjectMappingFromUserProfile> friendObjectsList = new ArrayList<>();

            friendRepository.findAllByUsername(username).forEach(s -> friendsList.add(s.getFriendName()));

            for (String friendName : friendsList) {
                friendObjectsList.add(userProfileRepository.getByUsername(friendName));
            }

            friendsListMap.put("friends", friendObjectsList);

            return friendsListMap;
        } else {
            throw new NullPointerException(username + " -> 없는 username입니다.");
        }
    }

    public Map<String, List<FriendObjectMappingFromUserProfile>> getFriendsRecommend() {
        Map<String, List<FriendObjectMappingFromUserProfile>> friendsRecommendListMap = new HashMap<>();
        List<FriendObjectMappingFromUserProfile> friendsRecommendList = new ArrayList<>();
        HashSet<Integer> pageSet = new HashSet<>();

        Random random = new Random();

        long dataSize = userProfileRepository.count();

        if (dataSize <= 12) { // 서비스가 너무 작을 때
            friendsRecommendList = userProfileRepository.findAllByOrderByModifiedAtDesc();
            Collections.shuffle(friendsRecommendList);
            friendsRecommendListMap.put("recommendFriends", friendsRecommendList);
            return friendsRecommendListMap;
        } else if (dataSize <= 16) { // 서비스가 너무 작을 때
            pageSet.add(1);
            pageSet.add(2);
            pageSet.add(3);
        } else {
            while (pageSet.size() <= 4) { // 랜덤 추천을 해줄 수 있을 정도로 서버가 커졌을 때
                pageSet.add(random.nextInt((int) (dataSize / 3)));
            }
        }

        for (int i = 0; i < pageSet.size(); i++) {
            Pageable pageable = PageRequest.of(random.nextInt(), 3);
            userProfileRepository.findAllByOrderByCreatedAtDesc(pageable).forEach(friendsRecommendList::add);
        }
        Collections.shuffle(friendsRecommendList);
        friendsRecommendListMap.put("recommendFriends", friendsRecommendList);
        return friendsRecommendListMap;
    }

    @Transactional
    public Map<String, String> requestFriend(FriendRequestRequestDto requestDto) {
        Map<String, String> requestFriendMsgMap = new HashMap<>();
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent() && userRepository.findByUsername(requestDto.getFriendName()).isPresent()) {
            if (friendRequestRepository.findAllByUsernameAndFriendName(requestDto.getUsername(), requestDto.getFriendName()).isEmpty()) {
                friendRequestRepository.save(new FriendRequest(requestDto.getUsername(), requestDto.getFriendName()));
                requestFriendMsgMap.put("msg", "friend request " + requestDto.getUsername() + " to " + requestDto.getFriendName() + " has been completed.");
                return requestFriendMsgMap;
            } else {
                requestFriendMsgMap.put("msg", "friend request " + requestDto.getUsername() + " to " + requestDto.getFriendName() + " is already in progress.");
                return requestFriendMsgMap;
            }
        } else {
            requestFriendMsgMap.put("msg", "Unregistered users.");
            return requestFriendMsgMap;
//            throw new NullPointerException("존재하지 않는 유저입니다.");
        }
    }

    public Map<String, Boolean> requestFriendChecker(String username, String friendName) {
        Map<String, Boolean> requestFriendCheckerMap = new HashMap<>();
        requestFriendCheckerMap.put("friend request " + username + " to " + friendName, !friendRequestRepository.findAllByUsernameAndFriendName(username, friendName).isEmpty());
        requestFriendCheckerMap.put("friend request " + friendName + " to " + username, !friendRequestRepository.findAllByUsernameAndFriendName(friendName, username).isEmpty());
        return requestFriendCheckerMap;
    }
}
