package com.facebook.facebookclone.service;

import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.repository.UserRepository;
import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import com.facebook.facebookclone.repository.mapping.UsernameMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSearchService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public String searchName(String username) {
        Optional<UsernameMapping> usernameMapping = userRepository.findFirstByUsernameContaining(username);
//        if (usernameMapping.isPresent()) {
//            return usernameMapping.get().getUsername().replaceAll("[0-9]", "");
//        } else {
//            return "";
//        }
        // 아래는 윗 주석을 인텔리제이에서 자동으로 바꿔준 코드
        return usernameMapping.map(mapping -> mapping.getUsername().replaceAll("[0-9]", "")).orElse("");
    }

    public HashSet<String> getUsernameSet(String username) {
        HashSet<String> usernameSet = new HashSet<>();
        userProfileRepository.findAllByUsernameContaining(username).forEach(s -> usernameSet.add(s.getUsername().replaceAll("[0-9]", "")));
        return usernameSet;
    }

    public List<FriendObjectMappingFromUserProfile> getExactUsernameList(String username) {
        return userProfileRepository.findAllByUsernameContaining(username);
    }
}

