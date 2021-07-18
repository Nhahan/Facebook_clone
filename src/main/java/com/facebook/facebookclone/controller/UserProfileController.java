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

    @PostMapping("/user/userprofile/picture") // 프로필 사진 등록
    public void createPicture(@RequestBody UserProfileRequestDto requestDto) {
        userProfileService.createPicture(requestDto);
    }

    @PostMapping("/user/userprofile/cover") // 커버 등록
    public void createCover(@RequestBody UserProfileRequestDto requestDto) {
        userProfileService.createCover(requestDto);
    }

    @GetMapping("/user/userprofile/picture/{username}") // 프로필 사진 조회
    public String getPicture(@PathVariable String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        return userProfile.getPicture();
    }

    @GetMapping("/user/userprofile/cover/{username}") // 커버 조회
    public String getCover(@PathVariable String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username);
        return userProfile.getCover();
    }

    @GetMapping("/user/userprofile") // 프로필 사진, 커버 동시 조회
    public UserProfile getUserProfile(@RequestParam(value = "username", required = true) String username) {
        return userProfileRepository.findByUsername(username);
    }

    @PutMapping("/user/userprofile/picture") // 프로필 사진 등록
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
