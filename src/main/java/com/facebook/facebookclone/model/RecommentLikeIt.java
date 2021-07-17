package com.facebook.facebookclone.model;

import com.facebook.facebookclone.dto.RecommentLikeItRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RecommentLikeIt extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long recommentId;

    public RecommentLikeIt(RecommentLikeItRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.recommentId = requestDto.getRecommentId();
    }
}
