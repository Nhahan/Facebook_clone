package com.facebook.facebookclone.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class TestComment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tcommentId")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "tarticle_id")
    private TestArticle testArticle;

    public TestComment(TestArticle testArticle) {
        this.testArticle = testArticle;
        this.username = "testUsername";
        this.content = "testContent";
    }
}
