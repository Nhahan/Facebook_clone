package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.CommentRequestDto;
import com.facebook.facebookclone.model.Comment;
import com.facebook.facebookclone.repository.CommentRepository;
import com.facebook.facebookclone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @GetMapping("/user/comment/{username}/{articleId}")
    public List<Comment> readComments(@PathVariable String username, @PathVariable Long articleId) {
        List<Comment> commentList = commentRepository.findAllByUsernameAndArticleId(username, articleId);
        return commentService.getCommentWithLikeItCounter(commentList, username);
    }

    @PostMapping("/user/comment")
    public void createComment(@RequestBody CommentRequestDto requestDto) {
        commentService.createComment(requestDto);
    }

    @PutMapping("/user/comment/{commentId}")
    public void putComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        commentService.putComment(commentId, requestDto);
    }

    @DeleteMapping("/user/comment/{username}/{articleId}")
    public void deleteComment(@PathVariable String username, @PathVariable Long articleId) {
        commentService.deleteComment(username, articleId);
    }
}
