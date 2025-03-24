package app.api.service;

import app.api.entity.Article;
import app.api.exception.CustomRetryException;
import app.api.repository.ArticleRepositoryImpl;
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
@EnableAsync
public class ArticleService {

  public ArticleRepositoryImpl articleRepository;

  @Autowired
  public ArticleService(ArticleRepositoryImpl articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Async
  public CompletableFuture<Article> addArticle(Article article) {
    Article addedArticle = articleRepository.addArticle(article);
    if (addedArticle != null) {
      log.info("Added article: {}", addedArticle);
    } else {
      log.info("Article {} already exists", article.getName());
    }
    return CompletableFuture.completedFuture(addedArticle);
  }

  public Article updateArticle(Article article) {
    Article updatedArticle = articleRepository.updateArticle(article);
    if (updatedArticle != null) {
      log.info("Updated article: {}", updatedArticle);
    } else {
      log.info("Article {} does not exist", article.getName());
    }
    return updatedArticle;
  }

  //Обеспечение удаления статьи
  @Retryable(
      value = CustomRetryException.class,
      maxAttempts = 5,
      backoff = @Backoff(delay = 10000)
  )
  public Article deleteArticle(int id) {
    Article deletedArticle = articleRepository.deleteArticle(id);
    if (deletedArticle != null) {
      log.info("Deleted article: {}", deletedArticle);
    } else {
      log.info("Article with id {} does not exist", id);
    }
    return deletedArticle;
  }

  public Article findArticleById(int id) {
    Article article = articleRepository.findArticleById(id);
    if (article != null) {
      log.info("Found article: {}", article.getName());
    } else {
      log.info("Article with id {} does not exist", id);
    }
    return article;
  }

  public Article patchArticle(Article article) {
    Article updatedArticle = articleRepository.patchArticle(article);
    if (updatedArticle != null) {
      log.info("Patched article: {}", updatedArticle.getName());
    } else {
      log.info("Article with id {} does not exist", updatedArticle.getId().getId());
    }
    return updatedArticle;
  }

}
