package com.facebook.facebookclone.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class TestComment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @Column
    private Long testArticleId;

    public TestComment() {
        this.testArticleId = 1L;
        this.username = "testUsername";
        this.content = "testContent";
    }
}
