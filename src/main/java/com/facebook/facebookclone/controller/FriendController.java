package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.repository.FriendRepository;
import com.facebook.facebookclone.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;
    private final FriendRepository friendRepository;
    
}
