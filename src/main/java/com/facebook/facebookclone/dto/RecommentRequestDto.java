package com.facebook.facebookclone.dto;

import lombok.Getter;

@Getter
public class RecommentRequestDto {
    private Long commentId;
    private String username;
    private String content;
}
