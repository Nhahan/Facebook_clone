package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserProfileRepository userProfileRepository;
}
