package app.api.controller;

import app.api.entity.Article;
import app.api.service.ArticleService;
import app.api.controller.interfaceDrivenControllers.ArticleControllerInterface;
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
  public ResponseEntity<CompletableFuture<Article>> addArticle(Article article) {
    return rateLimiter.executeSupplier(() -> {
      log.info("Adding article: {}", article.getName());
      CompletableFuture<Article> addedArticle = articlesService.addArticle(article);
      return new ResponseEntity<>(addedArticle, HttpStatus.CREATED);
    });
  }

  @Override
  public ResponseEntity<Article> deleteArticle(int id) {
    return rateLimiter.executeSupplier(() -> {
      log.info("Deleting article with id: {}", id);
      Article deletedArticle = articlesService.deleteArticle(id);
      return new ResponseEntity<>(deletedArticle, HttpStatus.OK);
    });
  }

  @Override
  public ResponseEntity<Article> getArticle(int id) {
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

  @Override
  public ResponseEntity<Article> patchArticle(Article article) {
    return rateLimiter.executeSupplier(() -> {
      log.info("Patching article with id: {}", article.getId().getId());
      Article updatedArticle = articlesService.patchArticle(article);
      return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    });
  }
}
