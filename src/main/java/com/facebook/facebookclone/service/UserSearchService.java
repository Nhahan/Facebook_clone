package com.facebook.facebookclone.service;

import com.facebook.facebookclone.model.UserProfile;
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
    private final FriendRequestRepository friendRequestRepository;

    public HashSet<String> getUsernameSet(String username) {
        HashSet<String> usernameSet = new HashSet<>();
        userProfileRepository.findAllByUsernameContaining(username).forEach(s -> usernameSet.add(s.getUsername().replaceAll("[0-9]", "")));
        return usernameSet;
    }

    public List<Map<String, Object>> getExactUsernameList(String username, String friendName) {
        List<Map<String, Object>> userProfileMapList = new ArrayList<>();
        for (FriendObjectMappingFromUserProfile friendObjectMappingFromUserProfile: userProfileRepository.findAllByUsernameContaining(friendName)) {
            Map<String, Object> userProfileMap = new HashMap<>();
            userProfileMap.put("username", friendObjectMappingFromUserProfile.getUsername());
            userProfileMap.put("picture", friendObjectMappingFromUserProfile.getPicture());
            userProfileMap.put("changeRequestFriendChecker", !friendRequestRepository.findAllByUsernameAndFriendName(username, friendObjectMappingFromUserProfile.getUsername()).isEmpty());
            userProfileMapList.add(userProfileMap);
        }
        return userProfileMapList;
    }

    public List<String> getAllUsername() {
        List<String> allUsernameList = new ArrayList<>();
        userProfileRepository.findAll().forEach(s -> allUsernameList.add(s.getUsername()));
        return allUsernameList;
    }
}

