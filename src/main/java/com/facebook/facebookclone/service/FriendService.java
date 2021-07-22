package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.FriendRequestRequestDto;
import com.facebook.facebookclone.model.Friend;
import com.facebook.facebookclone.model.FriendRequest;
import com.facebook.facebookclone.repository.FriendRepository;
import com.facebook.facebookclone.repository.FriendRequestRepository;
import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.repository.UserRepository;
import com.facebook.facebookclone.repository.mapping.FriendMapping;
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
        String username = requestDto.getUsername();
        String friendName = requestDto.getFriendName();
        if (friendRequestRepository.findAllByUsernameAndFriendName(friendName, username).isEmpty()) {
            throw new NullPointerException("친구 신청이 없습니다.");
        } else {
            if (friendRepository.findAllByUsernameAndFriendName(username, friendName).isEmpty()) {
                friendRepository.save(new Friend(username, friendName));
                if (friendRepository.findAllByUsernameAndFriendName(friendName, username).isEmpty()) {
                    friendRepository.save(new Friend(friendName, username));
                }
            // 서로의 친구 요청 모두 삭제
            friendRequestRepository.deleteByUsernameAndFriendName(username, friendName);
            friendRequestRepository.deleteByUsernameAndFriendName(friendName, username);
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
            List<FriendObjectMappingFromUserProfile> friendObjectsList = new ArrayList<>();

            for (FriendMapping name : friendRepository.findAllByUsername(username)) {
                FriendObjectMappingFromUserProfile friend = userProfileRepository.getByUsername(name.getFriendName());
                friendObjectsList.add(friend);
            }

            friendsListMap.put("friends", friendObjectsList);

            return friendsListMap;
        } else {
            throw new NullPointerException(username + " -> 없는 username입니다.");
        }
    }

    public Map<String, List<FriendObjectMappingFromUserProfile>> getFriendsRecommend(String username) {
        Map<String, List<FriendObjectMappingFromUserProfile>> friendsRecommendListMap = new HashMap<>();
        List<FriendObjectMappingFromUserProfile> friendsRecommendList = new ArrayList<>();
        HashSet<Integer> pageSet = new HashSet<>();

        Random random = new Random();

        long totalUserDataSize = userProfileRepository.count() - 1; // 자신 제외
        final int dataSizeWhenServiceIsSmall = 20;
        final int dataReturnSize = 12; // 리턴할 유저 데이터 수
        final int totalPages = 4; // 몇 페이지로? 
        final int sizePerPage = 4; // totalPages * sizePerPage > 12

        if (totalUserDataSize <= dataSizeWhenServiceIsSmall) { // 서비스가 너무 작을 때
            List<FriendObjectMappingFromUserProfile> tempUserList;
            tempUserList = userProfileRepository.findAllByOrderByModifiedAtDesc();
            Collections.shuffle(tempUserList);
            for (FriendObjectMappingFromUserProfile userProfile : tempUserList) {
                if (!username.equals(userProfile.getUsername())) {
                    friendsRecommendList.add(userProfile); // 자신 제외
                }
                if (friendsRecommendList.size() == dataReturnSize) { // 리턴할 데이터 수가 차면 break;
                    break;
                }
            }
            friendsRecommendListMap.put("recommendFriends", friendsRecommendList);
            return friendsRecommendListMap;
        } else { // 랜덤 추천을 해줄 수 있을 정도로 서버가 커졌을 때
            while (pageSet.size() <= totalPages) {
                pageSet.add(random.nextInt((int) (totalUserDataSize / sizePerPage)));
            }
        }

        for (int i = 0; i < pageSet.size(); i++) {
            Pageable pageable = PageRequest.of(random.nextInt(), sizePerPage);
            userProfileRepository.findAllByOrderByCreatedAtDesc(pageable).forEach(friendsRecommendList::add);
        }
        Collections.shuffle(friendsRecommendList);

        List<FriendObjectMappingFromUserProfile> finalRecommendList = new ArrayList<>();

        for (FriendObjectMappingFromUserProfile finalUserProfile : friendsRecommendList) {
            if (!username.equals(finalUserProfile.getUsername())) {
                finalRecommendList.add(finalUserProfile); // 자신 제외
            }
            if (friendsRecommendList.size() == dataReturnSize) { // 리턴할 데이터 수가 차면 break;
                break;
            }
        }

        friendsRecommendListMap.put("recommendFriends", finalRecommendList);
        return friendsRecommendListMap;
    }

    @Transactional
    public Map<String, String> requestFriend(FriendRequestRequestDto requestDto) {
        String username = requestDto.getUsername();
        String friendName = requestDto.getFriendName();

        if (username.equals(friendName)) {
            throw new IllegalArgumentException("자신에게 친구신청을 할 수 없습니다.");
        }
        Map<String, String> requestFriendMsgMap = new HashMap<>();
        if (userRepository.findByUsername(username).isPresent() && userRepository.findByUsername(friendName).isPresent()) {
            if (friendRequestRepository.findAllByUsernameAndFriendName(username, friendName).isEmpty()) {
                friendRequestRepository.save(new FriendRequest(username, friendName));
                requestFriendMsgMap.put("code", "200");
                requestFriendMsgMap.put
                        ("msg", "friend request " + username + " to " + friendName + " has been completed.");
            } else {
                requestFriendMsgMap.put("code", "209");
                requestFriendMsgMap.put
                        ("msg", "friend request " + username + " to " + friendName + " is already in progress.");
            }
        } else {
            requestFriendMsgMap.put("code", "500");
            requestFriendMsgMap.put("msg", "Unregistered users.");
        }
        return requestFriendMsgMap;
    }

    public Map<String, Boolean> requestFriendChecker(String username, String friendName) {
        Map<String, Boolean> requestFriendCheckerMap = new HashMap<>();
        requestFriendCheckerMap.put
                ("requestChecker", !friendRequestRepository.findAllByUsernameAndFriendName(username, friendName).isEmpty());
        return requestFriendCheckerMap;
    }

    public List<FriendObjectMappingFromUserProfile> getReceivedRequestFriendList(String username) {
        List<FriendObjectMappingFromUserProfile> friendProfileList = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequestRepository.findAllByFriendName(username)) {
            String requestName = friendRequest.getUsername();
            friendProfileList.add(userProfileRepository.getByUsername(requestName));
        }
        return friendProfileList;
    }

    public List<FriendObjectMappingFromUserProfile> getGivenRequestFriendList(String username) {
        List<FriendObjectMappingFromUserProfile> friendProfileList = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequestRepository.findAllByUsername(username)) {
            String friendName = friendRequest.getFriendName();
            friendProfileList.add(userProfileRepository.getByUsername(friendName));
        }
        return friendProfileList;
    }

    @Transactional
    public void declineReceivedFriend(String username, String friendName) {
        friendRequestRepository.deleteByUsernameAndFriendName(friendName, username);
    }

    @Transactional
    public void declineGivenFriend(String username, String friendName) {
        friendRequestRepository.deleteByUsernameAndFriendName(username, friendName);
    }
}
