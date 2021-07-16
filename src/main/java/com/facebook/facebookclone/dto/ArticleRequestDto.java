package com.facebook.facebookclone.dto;

import lombok.Getter;

@Getter
public class ArticleRequestDto {
    private String username;
    private String content;
    private String picture;
    private String video;
}