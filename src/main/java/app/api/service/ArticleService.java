package app.api.service;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.repository.ArticleRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleService {

  public ArticleRepositoryImpl articleRepository;

  @Autowired
  public ArticleService(ArticleRepositoryImpl articleRepository) {
    this.articleRepository = articleRepository;
  }

  public Article addArticle(Article article) {
    Article addedArticle = articleRepository.addArticle(article);
    if (addedArticle != null) {
      log.info("Added article: {}", addedArticle);
    } else {
      log.info("Article {} already exists", article.getName());
    }
    return addedArticle;
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
