package com.facebook.facebookclone.dto;

import lombok.Getter;

@Getter
public class TestCommentRequestDto {
    private Long articleId;
    private String username;
    private String content;
}
