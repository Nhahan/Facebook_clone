package com.facebook.facebookclone.model;

import com.facebook.facebookclone.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long articleId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @Transient
    private Long recommentCount;

    @Transient
    private Long commentLikeItCount;

    @Transient
    private Boolean commentLikeItChecker;

    public Comment(CommentRequestDto requestDto) {
        this.articleId = requestDto.getArticleId();
        this.username = requestDto.getUsername();
        this.content = requestDto.getContent();
    }

    public void addRecommentCount(Long count) {
        this.recommentCount = count;
    }

    public void addCommentLikeItCount(Long count) {
        this.commentLikeItCount = count;
    }

    public void changeLikeItChecker(Boolean trueOrFalse) {
        this.commentLikeItChecker = trueOrFalse;
    }

    public void commentUpdate(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
