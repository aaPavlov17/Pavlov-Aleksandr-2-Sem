package app.api.controller;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.CategoryId;
import app.api.service.ArticleService;
import app.api.controller.interfaceDrivenControllers.ArticleControllerInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ArticleController implements ArticleControllerInterface {

  private final ArticleService articlesService;

  @Autowired
  public ArticleController(ArticleService articlesService) {
    this.articlesService = articlesService;
  }

  @Override
  public ResponseEntity<Article> addArticle(Article article) {
    log.info("Adding article: {}", article.getName());
    try {
      Article AddedArticle = articlesService.addArticle(article);
      log.info("Added article: {}", AddedArticle.getName());
      return new ResponseEntity<>(AddedArticle, HttpStatus.CREATED);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Article> deleteArticle(int id) {
    log.info("Deleting article with id: {}", id);
    try {
      Article DeletedArticle = articlesService.deleteArticle(id);
      log.info("Deleted article: {}", DeletedArticle.getName());
      return new ResponseEntity<>(DeletedArticle, HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Article> getArticle(int id) {
    log.info("Getting article with id: {}", id);
    try {
      Article article = articlesService.findArticleById(id);
      log.info("Found article: {}", article.getName());
      return new ResponseEntity<>(article, HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Article> putArticle(Article article) {
    log.info("Updating article: {}", article.getName());
    try {
      Article updatedArticle = articlesService.updateArticle(article);
      log.info("Updated article: {}", updatedArticle.getName());
      return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<Article> patchArticle(Article article) {
    log.info("Patching article with id: {}", article.getId().getId());
    try {
      Article updatedArticle = articlesService.patchArticle(article);
      log.info("Patched article: {}", updatedArticle.getName());
      return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
