package com.facebook.facebookclone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonBackReference
    @OneToMany(mappedBy = "testArticle", fetch = FetchType.EAGER)
    private List<TestComment> commentList;

    public TestArticle(String username) {
        this.username = username;
        this.content = "testArticle";
    }
}