package com.facebook.facebookclone.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class TestArticle extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @Column(length = 12, nullable = false)
    private String username;

    @Column(nullable = false)
    @Lob
    private String content;

    @OneToMany
    @JoinColumn(name = "testArticleId", nullable = true)
    private List<TestComment> commentList;

    public TestArticle() {
        this.username = "testName";
        this.content = "testArticle";
    }
}