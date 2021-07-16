package com.facebook.facebookclone.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long articleId;
    private String username;
    private String content;
}
