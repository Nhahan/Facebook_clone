package com.facebook.facebookclone.service;

import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserSearchService {

    private final UserProfileRepository userProfileRepository;

    public HashSet<String> getUsernameSet(String username) {
        HashSet<String> usernameSet = new HashSet<>();
        userProfileRepository.findAllByUsernameContaining(username).forEach(s -> usernameSet.add(s.getUsername().replaceAll("[0-9]", "")));
        return usernameSet;
    }

    public List<FriendObjectMappingFromUserProfile> getExactUsernameList(String username) {
        return userProfileRepository.findAllByUsernameContaining(username);
    }

    public List<String> getAllUsername() {
        List<String> allUsernameList = new ArrayList<>();
        userProfileRepository.findAll().forEach(s -> allUsernameList.add(s.getUsername()));
        return allUsernameList;
    }
}

