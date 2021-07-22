package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.UserProfileRequestDto;
import com.facebook.facebookclone.model.UserProfile;
import com.facebook.facebookclone.repository.UserProfileRepository;
import com.facebook.facebookclone.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserProfileRepository userProfileRepository;

    @GetMapping("/user/userprofile/picture/{username}") // 프로필 사진 조회
    public String getPicture(@PathVariable String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new NullPointerException("등록되지 않은 username"));
        return userProfile.getPicture();
    }

    @GetMapping("/user/userprofile/cover/{username}") // 커버 조회
    public String getCover(@PathVariable String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new NullPointerException("등록되지 않은 username"));
        return userProfile.getCover();
    }

    @PutMapping("/user/userprofile/picture") // 프로필 사진 수정
    public void uploadPicture(@RequestBody UserProfileRequestDto requestDto) {
        userProfileService.putPicture(requestDto);
    }

    @PutMapping("/user/userprofile/cover") // 커버 수정
    public void uploadCover(@RequestBody UserProfileRequestDto requestDto) {
        userProfileService.putCover(requestDto);
    }

    @DeleteMapping("/user/userprofile/picture/{username}") // 사진 삭제
    public void deletePicture(@PathVariable String username) {
        userProfileService.deletePicture(username);
    }

    @DeleteMapping("/user/userprofile/cover/{username}") // 사진 삭제
    public void deleteCover(@PathVariable String username) {
        userProfileService.deleteCover(username);
    }

    @GetMapping("/user/userprofile/pictureList/{username}") // 사진 목록
    public Map<String, List<String>> getPictureList(@PathVariable String username) {
        return userProfileService.getPictureList(username);
    }
}
