package app.e2e;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.repository.ArticleRepository;
import app.api.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class E2ETest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ArticleRepository articleRepository;

  private String baseUrl() {
    return "http://localhost:" + port;
  }

  @Test
  void testArticleAndCategoryCRUDOperations() {
    Category category = new Category(new CategoryId(1), "Tech");
    ResponseEntity<Category> categoryResponse = restTemplate.postForEntity(
        baseUrl() + "/categories/add_category",
        category,
        Category.class
    );

    assertEquals(HttpStatus.OK, categoryResponse.getStatusCode());
    CategoryId categoryId = categoryResponse.getBody().getId();
    assertNotNull(categoryId);

    Article article = new Article("Spring Boot Guide", new ArticleId(1), "url", categoryId);
    ResponseEntity<Article> articleResponse = restTemplate.postForEntity(
        baseUrl() + "/articles/add_article",
        article,
        Article.class
    );

    assertEquals(HttpStatus.CREATED, articleResponse.getStatusCode());
    Article createdArticle = articleResponse.getBody();
    assertNotNull(createdArticle);
    assertNotNull(createdArticle.getId());

    ResponseEntity<Article> getResponse = restTemplate.getForEntity(
        baseUrl() + "/articles/get_article/" + createdArticle.getId().getId(),
        Article.class,
        article.getId().getId()
    );

    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertNotNull(getResponse.getBody());
    assertEquals("Spring Boot Guide", getResponse.getBody().getName());

    createdArticle.setName("Updated Guide");
    HttpEntity<Article> updateRequest = new HttpEntity<>(createdArticle);
    ResponseEntity<Article> updateResponse = restTemplate.exchange(
        baseUrl() + "/articles/put_article",
        HttpMethod.PUT,
        updateRequest,
        Article.class
    );

    assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
    assertEquals("Updated Guide", updateResponse.getBody().getName());

    ResponseEntity<Void> deleteResponse = restTemplate.exchange(
        baseUrl() + "/articles/delete_article/" + createdArticle.getId().getId(),
        HttpMethod.DELETE,
        null,
        Void.class
    );

    assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

    ResponseEntity<Article> afterDeleteResponse = restTemplate.getForEntity(
        baseUrl() + "/articles/get_article/" + createdArticle.getId().getId(),
        Article.class,
        article.getId().getId()
    );

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, afterDeleteResponse.getStatusCode());
  }
}
