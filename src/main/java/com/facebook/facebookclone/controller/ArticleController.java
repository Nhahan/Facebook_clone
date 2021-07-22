package com.facebook.facebookclone.controller;

import com.facebook.facebookclone.dto.ArticleRequestDto;
import com.facebook.facebookclone.model.Article;
import com.facebook.facebookclone.repository.ArticleRepository;
import com.facebook.facebookclone.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    @GetMapping("/user/all-article/{username}/{page}/{size}") // 전체 게시글 조회
    public Page<Article> getAllArticleWithSize(
            @PathVariable String username,
            @PathVariable int page,
            @PathVariable int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Article> articleList = articleRepository.findAllByOrderByCreatedAtAsc(pageable);
        return articleService.getPagedArticleList(articleList, username);
    }

    @GetMapping("/user/my-article/{username}/{page}/{size}") // username의 전체 게시글 조회
    public Page<Article> getArticleWithSize(
            @PathVariable String username,
            @PathVariable int page,
            @PathVariable int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Article> articleList = articleRepository.findAllByUsernameOrderByCreatedAtAsc(username, pageable);
        return articleService.getPagedArticleList(articleList, username);
    }

    @GetMapping("/user/article/{articleId}") // 특정 게시글 조회
    public Article getArticleByArticleId(@PathVariable Long articleId) {
        return articleService.getArticleByArticleId(articleId);
    }

    @PostMapping("/user/article")
    public void createArticle(@RequestBody ArticleRequestDto requestDto) {
        articleService.createArticle(requestDto);
    }

    @PutMapping("/user/article/{articleId}")
    public void putArticle(@PathVariable Long articleId, @RequestBody ArticleRequestDto requestDto) {
        articleService.putArticle(articleId, requestDto);
    }

    @DeleteMapping("/user/article/{articleId}")
    public void deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
    }
}
