package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.FriendRequestDto;
import com.facebook.facebookclone.model.Friend;
import com.facebook.facebookclone.repository.FriendRepository;
import com.facebook.facebookclone.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public void addFriend(FriendRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent() && userRepository.findByUsername(requestDto.getFriendName()).isPresent()) {
            if (friendRepository.findAllByUsernameAndFriendName(requestDto.getUsername(), requestDto.getFriendName()).isEmpty()) {
                friendRepository.save(new Friend(requestDto));
            } else {
                throw new IllegalArgumentException(requestDto.getUsername() + "&" + requestDto.getFriendName() + " -> 이미 등록된 친구입니다.");
            }
        } else {
            throw new NullPointerException("존재하지 않는 유저입니다.");
        }
    }

    @Transactional
    public void deleteFriend(FriendRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent() && userRepository.findByUsername(requestDto.getFriendName()).isPresent()) {
            if (friendRepository.findAllByUsernameAndFriendName(requestDto.getUsername(), requestDto.getFriendName()).isEmpty()) {
                throw new IllegalArgumentException(requestDto.getUsername() + "&" + requestDto.getFriendName() + " -> 친구가 아닙니다.");
            } else {
                friendRepository.deleteAllByUsernameAndFriendName(requestDto.getUsername(), requestDto.getFriendName());
            }
        } else {
            throw new NullPointerException("존재하지 않는 유저입니다.");
        }
    }

    public Map<String, List<String>> myFriendsList(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            Map<String, List<String>> friendsListMap = new HashMap<>();
            List<String> friendsList = new ArrayList<>();

            friendRepository.findAllByUsername(username).forEach(s -> friendsList.add(s.getFriendName()));
            friendsListMap.put("friends", friendsList);
            return friendsListMap;
        } else {
            throw new NullPointerException(username + " -> 없는 username입니다.");
        }
    }

    public Map<String, List<String>> getFriendsRecommend() {
        Map<String, List<String>> friendsRecommendListMap = new HashMap<>();
        List<String> friendsRecommendList = new ArrayList<>();
        HashSet<Integer> pageSet = new HashSet<>();

        Random random = new Random();

        long dataSize = userRepository.count();

        if (dataSize <= 12) { // 서비스가 너무 작을 때
            userRepository.findAll().forEach(s -> friendsRecommendList.add(s.getUsername()));
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
            userRepository.findAllByOrderByCreatedAtDesc(pageable).forEach(s -> friendsRecommendList.add(s.getUsername()));
        }
        Collections.shuffle(friendsRecommendList);
        friendsRecommendListMap.put("recommendFriends", friendsRecommendList);
        return friendsRecommendListMap;
    }
}
