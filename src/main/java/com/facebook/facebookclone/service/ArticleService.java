package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.ArticleRequestDto;
import com.facebook.facebookclone.model.Article;
import com.facebook.facebookclone.model.Comment;
import com.facebook.facebookclone.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserProfileRepository userProfileRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;
    private final ArticleLikeItRepository articleLikeItRepository;

    @Transactional
    public void createArticle(ArticleRequestDto requestDto) {
        Article article = new Article(requestDto);
        articleRepository.save(article);
    }

    @Transactional
    public void putArticle(Long articleId, ArticleRequestDto requestDto) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        article.articleUpdate(requestDto);
    }

    public Page<Article> getPagedArticleList(Page<Article> articleList, String username) {
        for (Article value : articleList) {
            Long articleId = value.getId();
            List<Comment> commentList = commentRepository.findAllByArticleId(articleId);
            Long recommentCount = 0L;
            String usernamePicture = "";
            if (userProfileRepository.findByUsername(value.getUsername()).isPresent()) {
                usernamePicture = userProfileRepository.findByUsername(value.getUsername()).get().getPicture();
            }
            for (Comment comment : commentList) { // 대댓글 갯수
                recommentCount += recommentRepository.countByCommentId(comment.getId());
            }

            // username의 프로필 사진
            value.addUsernamePicture(usernamePicture);

            // 댓글 갯수
            Long commentCount = commentRepository.countByArticleId(articleId);

            // 좋아요 갯수
            Long likesCount = articleLikeItRepository.countByArticleId(articleId);

            value.addCommentCount(commentCount + recommentCount);
            value.addLikeItCount(likesCount);

            // username의 좋아요 여부
            value.changeLikeItChecker(articleLikeItRepository.findByUsernameAndArticleId(username, articleId).isPresent());

            articleLikeItRepository.findAllByArticleId(articleId).forEach(value::addLikeItUserList);
        }
        return articleList;
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
        for (Comment comment : commentRepository.findAllByArticleId(articleId)) {
            Long commentId = comment.getId();
            recommentRepository.deleteByCommentId(commentId);
        }
        commentRepository.deleteAllByArticleId(articleId);
    }

    public Article getArticleByArticleId(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new NullPointerException("존재하지 않는 articleId"));
    }
}
