package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.repository.mapping.FriendObjectMappingFromUserProfile;
import com.facebook.facebookclone.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserSearchController {

    private final UserSearchService userSearchService;

    @GetMapping("/user/search/exist/{username}")
    public String nameExist(@PathVariable String username) {
        return userSearchService.searchName(username);
    }

    @GetMapping("/user/search/contain-list/{username}")
    public HashSet<String> nameContainList(@PathVariable String username) {
        return userSearchService.getUsernameSet(username);
    }

    @GetMapping("/user/search/exact-list/{username}")
    public List<FriendObjectMappingFromUserProfile> nameName(@PathVariable String username) {
        return userSearchService.getExactUsernameList(username);
    }
}
