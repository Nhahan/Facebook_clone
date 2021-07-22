package com.facebook.facebookclone.service;

import com.facebook.facebookclone.repository.FriendRepository;
import com.facebook.facebookclone.repository.FriendRequestRepository;
import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserSearchService {

    private final UserProfileRepository userProfileRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;

    public HashSet<String> getUsernameSet(String username, String friendName) {
        HashSet<String> usernameSet = new HashSet<>();
        for (FriendObjectMappingFromUserProfile userProfile : userProfileRepository.findAllByUsernameContaining(friendName)) {
            if (friendRepository.findAllByUsernameAndFriendName(username, userProfile.getUsername()).isEmpty() && !username.equals(userProfile.getUsername())) { // 친구 목록에 없으면 검색결과에 반영 & username 제외
                usernameSet.add(userProfile.getUsername().replaceAll("[0-9]", ""));
            }
        }
        return usernameSet;
    }

    public List<Map<String, Object>> getExactUsernameList(String username, String friendName) {
        List<Map<String, Object>> userProfileMapList = new ArrayList<>();
        for (FriendObjectMappingFromUserProfile friendObjectMappingFromUserProfile : userProfileRepository.findAllByUsernameContaining(friendName)) {
            if (friendRepository.findAllByUsernameAndFriendName(username, friendObjectMappingFromUserProfile.getUsername()).isEmpty() && !username.equals(friendObjectMappingFromUserProfile.getUsername())) { // 친구 목록에 없으면 검색결과에 반영 & username 제외
                Map<String, Object> userProfileMap = new HashMap<>();
                userProfileMap.put("username", friendObjectMappingFromUserProfile.getUsername());
                userProfileMap.put("picture", friendObjectMappingFromUserProfile.getPicture());
                userProfileMap.put("changeRequestFriendChecker", !friendRequestRepository.findAllByUsernameAndFriendName(username, friendObjectMappingFromUserProfile.getUsername()).isEmpty());
                userProfileMapList.add(userProfileMap);
            }
        }
        return userProfileMapList;
    }

    public List<String> getAllUsername() {
        List<String> allUsernameList = new ArrayList<>();
        userProfileRepository.findAll().forEach(user -> allUsernameList.add(user.getUsername()));
        return allUsernameList;
    }
}

