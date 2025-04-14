package app.api.controller;

import app.api.entity.Article;
import app.api.service.ArticleService;
import app.api.controller.interfaceDrivenControllers.ArticleControllerInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class ArticleController implements ArticleControllerInterface {

  private final ArticleService articlesService;
  private final RateLimiter rateLimiter = RateLimiter.ofDefaults("articleRateLimiter");

  @Autowired
  public ArticleController(ArticleService articlesService) {
    this.articlesService = articlesService;
  }

  @Override
  public ResponseEntity<Article> addArticle(Article article) {
    return rateLimiter.executeSupplier(() -> {
      log.info("Adding article: {}", article.getName());
      Article addedArticle = null;
      try {
        addedArticle = articlesService.saveArticle(article);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      return new ResponseEntity<>(addedArticle, HttpStatus.CREATED);
    });
  }

  @Override
  public ResponseEntity<?> deleteArticle(Long id) {
    return rateLimiter.executeSupplier(() -> {
      log.info("Deleting article with id: {}", id);
      articlesService.deleteArticle(id);
      return new ResponseEntity<>(HttpStatus.OK);
    });
  }

  @Override
  public ResponseEntity<Article> getArticle(Long id) {
    return rateLimiter.executeSupplier(() -> {
      log.info("Getting article with id: {}", id);
      Article article = articlesService.findArticleById(id);
      return new ResponseEntity<>(article, HttpStatus.OK);
    });
  }

  @Override
  public ResponseEntity<Article> putArticle(Article article) {
    return rateLimiter.executeSupplier(() -> {
      log.info("Updating article: {}", article.getName());
      Article updatedArticle = articlesService.updateArticle(article);
      return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    });
  }
}
