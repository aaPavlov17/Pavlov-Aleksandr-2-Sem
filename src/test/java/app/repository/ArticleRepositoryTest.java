package app.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.repository.ArticleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleRepositoryTest {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
      .withDatabaseName("testdb")
      .withUsername("test")
      .withPassword("test");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  private ArticleRepository articleRepository;

  @Test
  @Order(1)
  void shouldSaveAndFindArticle() {
    final var articleId = new ArticleId(1L);
    final var article = Article.builder().name("a").id(articleId).url("123").build();

    articleRepository.save(article);
    final var found = articleRepository.findById(articleId).orElse(null);

    assertThat(found).isNotNull();
    assertThat(found).isEqualTo(article);
  }

  @Test
  @Order(2)
  void shouldDeleteArticle() {
    final var articleId = new ArticleId(2L);

    articleRepository.save(Article.builder().name("a").id(articleId).url("123").build());

    articleRepository.deleteById(articleId);

    assertThat(articleRepository.findById(articleId)).isEmpty();
  }
}
