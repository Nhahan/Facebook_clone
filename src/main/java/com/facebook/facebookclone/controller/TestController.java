package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.model.TestArticle;
import com.facebook.facebookclone.model.TestComment;
import com.facebook.facebookclone.repository.TestArticleRepository;
import com.facebook.facebookclone.repository.TestCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestArticleRepository articleRepository;
    private final TestCommentRepository commentRepository;

    // TestArticle
    @GetMapping("/test/article")
    public List<TestArticle> getTestArticleList() {
        return articleRepository.findAll();
    }

    @GetMapping("/test/article/{articleId}")
    public TestArticle getTestArticle(@PathVariable Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new NullPointerException("없는 id"));
    }

    @PostMapping("/test/article/{username}")
    public void createTestArticle(@PathVariable String username) {
        TestArticle article = new TestArticle(username);
        articleRepository.save(article);
    }

    // TestComment
    @GetMapping("/test/comment")
    public List<TestComment> getTestComment() {
        return commentRepository.findAll();
    }

    @PostMapping("/test/comment/{articleId}")
    public void createTestComment(@PathVariable Long articleId) {
        TestComment comment = new TestComment(articleId);
        commentRepository.save(comment);
    }
}
