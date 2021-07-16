package com.facebook.facebookclone.model;

import com.facebook.facebookclone.dto.ArticleLikeItRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ArticleLikeIt extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long articleId;

    public ArticleLikeIt(ArticleLikeItRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.articleId = requestDto.getArticleId();
    }
}
