package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.UserProfileRequestDto;
import com.facebook.facebookclone.model.UserProfile;
import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void putPicture(UserProfileRequestDto requestDto) {
        UserProfile userProfile = userProfileRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new NullPointerException("등록되지 않은 username"));
        userProfile.pictureUpdate(requestDto.getPicture());
    }

    @Transactional
    public void putCover(UserProfileRequestDto requestDto) {
        UserProfile userProfile = userProfileRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new NullPointerException("등록되지 않은 username"));
        userProfile.coverUpdate(requestDto.getCover());
    }

    @Transactional
    public void deletePicture(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username).orElseThrow(() -> new NullPointerException("등록되지 않은 username"));
        userProfile.pictureUpdate("");
    }

    @Transactional
    public void deleteCover(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username).orElseThrow(() -> new NullPointerException("등록되지 않은 username"));
        userProfile.coverUpdate("");
    }

    public Map<String, List<String>> getPictureList(String username) {
        Map<String, List<String>> pictureListMap = new HashMap<>();
        List<String> pictureList = new ArrayList<>();

        userProfileRepository.findAllByUsername(username).forEach(user -> pictureList.add(user.getPicture()));

        pictureListMap.put("pictures", pictureList);

        return pictureListMap;
    }
}
