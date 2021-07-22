package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.CommentRequestDto;
import com.facebook.facebookclone.model.Comment;
import com.facebook.facebookclone.repository.CommentLikeItRepository;
import com.facebook.facebookclone.repository.CommentRepository;
import com.facebook.facebookclone.repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;
    private final CommentLikeItRepository commentLikeItRepository;

    @Transactional
    public void createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);
    }

    @Transactional
    public void putComment(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        comment.commentUpdate(requestDto);
    }

    public List<Comment> getCommentWithLikeItCounter(List<Comment> commentList, String username) {
        for (Comment value : commentList) {
            Long commentId = value.getId();
            Long recommentCount = recommentRepository.countByCommentId(commentId);
            Long likesCount = commentLikeItRepository.countByCommentId(commentId);

            value.addRecommentCount(recommentCount);
            value.addCommentLikeItCount(likesCount);

            value.changeLikeItChecker(commentLikeItRepository.findByUsernameAndCommentId(username, commentId).isPresent());
        }
        return commentList;
    }

    @Transactional
    public void deleteComment(String username, Long articleId) {
        commentRepository.deleteByUsernameAndArticleId(username, articleId);
    }
}
