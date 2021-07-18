package com.facebook.facebookclone.service;

import com.facebook.facebookclone.dto.ArticleRequestDto;
import com.facebook.facebookclone.model.Article;
import com.facebook.facebookclone.model.ArticleLikeIt;
import com.facebook.facebookclone.model.Comment;
import com.facebook.facebookclone.repository.ArticleLikeItRepository;
import com.facebook.facebookclone.repository.ArticleRepository;
import com.facebook.facebookclone.repository.CommentRepository;
import com.facebook.facebookclone.repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
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

            for (Comment comment : commentList) { // 대댓글 갯수
                recommentCount += recommentRepository.countByCommentId(comment.getId());
            }

            Long commentCount = commentRepository.countByArticleId(articleId);
            Long likesCount = articleLikeItRepository.countByArticleId(articleId);

            value.addCommentCount(commentCount + recommentCount);
            value.addLikeItCount(likesCount);

            Optional<ArticleLikeIt> didUsernameLikeIt = Optional.ofNullable(articleLikeItRepository.findByUsernameAndArticleId(username, articleId));
            value.changeLikeItChecker(didUsernameLikeIt.isPresent());

            articleLikeItRepository.findAllByArticleId(articleId).forEach(value::addLikeItUserList);
        }
        return articleList;
    }
}
