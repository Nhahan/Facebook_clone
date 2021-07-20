package com.facebook.facebookclone.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class TestComment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tcomment_id")
    private Long id;

    @Column(nullable = false)
    private Long articleId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tarticle_Id")
    private TestArticle testArticle;

    public TestComment(Long articleId) {
        this.articleId = articleId;
        this.username = "testUsername";
        this.content = "testContent";
    }
}
