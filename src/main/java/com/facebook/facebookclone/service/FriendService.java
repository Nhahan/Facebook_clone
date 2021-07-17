package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.FriendRequestDto;
import com.facebook.facebookclone.model.Friend;
import com.facebook.facebookclone.repository.FriendRepository;
import com.facebook.facebookclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    public Map<String, List<String>> getFriendsRecommend(String username) {
//        Map<String, List<String>> friendsRecommendListMap = new HashMap<>();
//        List<String> friendsRecommendList = new ArrayList<>();
//        friendsRecommendList.add
//    }
}
