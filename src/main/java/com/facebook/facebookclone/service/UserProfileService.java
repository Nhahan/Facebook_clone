package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.UserProfileRequestDto;
import com.facebook.facebookclone.model.UserProfile;
import com.facebook.facebookclone.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public void createPicture(UserProfileRequestDto requestDto) {
        UserProfile userProfile = new UserProfile(requestDto);
        userProfileRepository.save(userProfile);
    }

    public void createCover(UserProfileRequestDto requestDto) {
        UserProfile userProfile = new UserProfile(requestDto);
        userProfileRepository.save(userProfile);
    }

    public void putPicture(UserProfileRequestDto requestDto) {
        UserProfile userProfile = userProfileRepository.findByUsername(requestDto.getUsername());
        userProfile.pictureUpdate(requestDto.getPicture());
    }

    public void putCover(UserProfileRequestDto requestDto) {
        UserProfile userProfile = userProfileRepository.findByUsername(requestDto.getUsername());
        userProfile.coverUpdate(requestDto.getCover());
    }

    public void deletePicture(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        userProfile.pictureUpdate("null");
    }

    public void deleteCover(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        userProfile.coverUpdate("null");
    }

}
