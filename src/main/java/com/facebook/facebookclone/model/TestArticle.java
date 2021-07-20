package com.facebook.facebookclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TestArticle extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tarticle_id")
    private Long id;

    @Column(length = 12, nullable = false)
    private String username;

    @Column(nullable = false)
    @Lob
    private String content;

    public TestArticle(String username) {
        this.username = username;
        this.content = "testArticle";
    }
}