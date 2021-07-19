package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.model.UserProfile;
import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import com.facebook.facebookclone.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserSearchController {

    private final UserSearchService userSearchService;

    @GetMapping("/user/search/contain-list/{username}") // 중복 이름 하나만
    public HashSet<String> nameContainList(@PathVariable String username) {
        return userSearchService.getUsernameSet(username);
    }

    @GetMapping("/user/search/exact-list/{username}/{friendName}") // 중복 이름 모두
    public List<Map<String, Object>> nameName(@PathVariable String username, @PathVariable String friendName) {
        return userSearchService.getExactUsernameList(username, friendName);
    }

    @GetMapping("/user/search/all") // 확인용
    public List<String> allUsername() {
        return userSearchService.getAllUsername();
    }
}
