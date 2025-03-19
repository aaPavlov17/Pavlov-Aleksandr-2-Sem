package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

  private List<Article> articles;

  public ArticleRepositoryImpl() {
    this.articles = new ArrayList<>();
  }

  @Override
  public Article findArticleById(int id) {
    ArticleId articleId = new ArticleId(id);
    for (Article article : articles) {
      if (article.getId().equals(articleId)) {
        return article;
      }
    }
    return null;
  }

  @Override
  public Article addArticle(Article article) {
    for (Article article1 : articles) {
      if (article1.getId().equals(article.getId())) {
        return null;
      }
    }
    articles.add(article);
    return article;
  }

  @Override
  public Article updateArticle(Article newArticle) {
    for (Article article : articles) {
      if (article.getId().equals(newArticle.getId())) {
        articles.set(articles.indexOf(article), newArticle);
        return newArticle;
      }
    }
    return null;
  }

  @Override
  public Article deleteArticle(int id) {
    ArticleId articleId = new ArticleId(id);
    for (Article article : articles) {
      if (article.getId().equals(articleId)) {
        articles.remove(article);
        return article;
      }
    }
    return null;
  }

  @Override
  public Article patchArticle(Article article) {
    for (Article article1 : articles) {
      if (article1.getId().equals(article.getId())) {
        articles.set(articles.indexOf(article), article);
        return article;
      }
    }
    return null;
  }
}
