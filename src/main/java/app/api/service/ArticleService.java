package app.api.service;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.exception.CustomRetryException;
import app.api.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ArticleService {

  public ArticleRepository articleRepository;

  @Autowired
  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Transactional
  public Article saveArticle(Article article) {
    return articleRepository.save(article);
  }

  @Transactional
  public Article updateArticle(Article article) {
   articleRepository.deleteById(article.getId());
   return articleRepository.save(article);
  }

  @Transactional
  public void deleteArticle(Long id) {
    articleRepository.deleteById(new ArticleId(id));
  }

  @Transactional
  public Article findArticleById(Long id) {
    return articleRepository.findById(new ArticleId(id)).orElse(null);
  }
}
