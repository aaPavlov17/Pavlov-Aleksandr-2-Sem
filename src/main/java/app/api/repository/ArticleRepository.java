package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;

public interface ArticleRepository {

  Article addArticle(Article article);

  Article updateArticle(Article article);

  Article deleteArticle(int id);

  Article findArticleById(int id);

  Article patchArticle(Article article);
}
