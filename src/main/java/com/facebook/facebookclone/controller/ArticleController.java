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

    @GetMapping("/user/article/{username}/{page}/{size}") // 게시글 조회 with size
    public Page<Article> getArticleWithSize(@PathVariable String username, @PathVariable int page, @PathVariable(required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Article> articleList = articleRepository.findAllByOrderByCreatedAtDesc(pageable);
        return articleService.getPagedArticleList(articleList, username);
    }

    @GetMapping("/user/article/{username}/{page}") // 게시글 조회 without size // 위와 합치려고 했는데 실패해서 따로 작성
    public Page<Article> getArticleWithoutSize(@PathVariable String username, @PathVariable int page) {
        Pageable pageable = PageRequest.of(page - 1, 3);
        Page<Article> articleList = articleRepository.findAllByOrderByCreatedAtDesc(pageable);
        return articleService.getPagedArticleList(articleList, username);
    }

    @PostMapping("/user/article")
    public void createArticle(@RequestBody ArticleRequestDto requestDto) { articleService.createArticle(requestDto);
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
