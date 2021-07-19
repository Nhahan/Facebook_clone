package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.RecommentRequestDto;
import com.facebook.facebookclone.model.Recomment;
import com.facebook.facebookclone.repository.RecommentRepository;
import com.facebook.facebookclone.service.RecommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecommentController {

    private final RecommentRepository recommentRepository;
    private final RecommentService recommentService;

    @GetMapping("/user/recomment/{username}/{commentId}") // 댓글 전체 조회
    public List<Recomment> readRecomments(@PathVariable String username, @PathVariable Long commentId) {
        List<Recomment> recommentList = recommentRepository.findAllByUsernameAndCommentId(username, commentId);
        return recommentService.recommentLikeItCounter(recommentList, username);
    }

    @PostMapping("/user/recomment")
    public void createRecomment(@RequestBody RecommentRequestDto requestDto) {
        recommentService.createRecomment(requestDto);
    }

    @PutMapping("/user/recomment/{recommentId}")
    public void putRecomment(@PathVariable Long recommentId, @RequestBody RecommentRequestDto requestDto) {
        recommentService.putRecomment(recommentId, requestDto);
    }

    @DeleteMapping("/user/recomment/{username}/{commentId}")
    public void deleteRecomment(@PathVariable String username, @PathVariable Long commentId) {
        recommentService.deleteRecomment(username, commentId);
    }
}
