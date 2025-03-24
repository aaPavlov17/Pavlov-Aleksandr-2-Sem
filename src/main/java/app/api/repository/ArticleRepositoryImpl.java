package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

  RestTemplate restTemplate;
  WebClient webClient;

  private List<Article> articles;

  public ArticleRepositoryImpl(RestTemplate restTemplate, WebClient webClient) {
    this.restTemplate = restTemplate;
    this.webClient = webClient;
    this.articles = new ArrayList<>();
  }

  public String fetchRandomUrl() {
    String url = "http://youtube.com/video/" + new Random().nextInt(10000);
    return restTemplate.getForObject(url, String.class);
  }

  public String fetchRandomDataBlocking() {
    String url = "http://youtube.com/video/" + new Random().nextInt(10000);
    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToMono(String.class)
        .block();
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
