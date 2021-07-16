package com.facebook.facebookclone.model;


import com.facebook.facebookclone.dto.ArticleRequestDto;
import com.facebook.facebookclone.repository.mapping.ArticleMemberMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Article extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(length = 12, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String picture;

    @Column(nullable = true)
    private String video;

    @Transient
    private Long commentCount;

    @Transient
    private Long articleLikeItCount;

    @Transient
    private Boolean articleLikeItChecker;

    @Transient
    private final List<String> articleLikeItUserList = new ArrayList<>();

    public void addCommentCount(Long count) {
        this.commentCount = count;
    }

    public void addLikeItCount(Long count) {
        this.articleLikeItCount = count;
    }

    public void changeLikeItChecker(Boolean trueOrFalse) {
        this.articleLikeItChecker = trueOrFalse;
    }

    public void addLikeItUserList(ArticleMemberMapping likeItUser) {
        this.articleLikeItUserList.add(likeItUser.getUsername());
    }

    public Article(ArticleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.picture = requestDto.getPicture();
        this.video = requestDto.getVideo();
        this.content = requestDto.getContent();
    }

    public void articleUpdate(ArticleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.picture = requestDto.getPicture();
        this.video = requestDto.getVideo();
        this.content = requestDto.getContent();
    }
}