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

    @GetMapping("/user/article") // 게시글 조회
    public Page<Article> getArticle(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Article> articleList = articleRepository.findAllByOrderByCreatedAtDesc(pageable);
        return articleService.getPagedArticleList(articleList, username);
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
        articleRepository.deleteById(articleId);
    }
}
