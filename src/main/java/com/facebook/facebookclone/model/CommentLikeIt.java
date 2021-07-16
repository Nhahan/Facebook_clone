package com.facebook.facebookclone.model;

import com.facebook.facebookclone.dto.CommentLikeItRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CommentLikeIt extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long commentId;

    public CommentLikeIt(CommentLikeItRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.commentId = requestDto.getCommentId();
    }
}
