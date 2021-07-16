package com.facebook.facebookclone.model;

import com.facebook.facebookclone.dto.CommentRequestDto;
import com.facebook.facebookclone.dto.RecommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Recomment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long commentId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @Transient
    private Long recommentLikeItCount;

    @Transient
    private Boolean recommentLikeItChecker;

    public Recomment(RecommentRequestDto requestDto) {
        super();
    }

    public void addRecommentLikeItCount(Long count) {
        this.recommentLikeItCount = count;
    }

    public void changeLikeItChecker(Boolean trueOrFalse) {
        this.recommentLikeItChecker = trueOrFalse;
    }

    public void recommentUpdate(RecommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
