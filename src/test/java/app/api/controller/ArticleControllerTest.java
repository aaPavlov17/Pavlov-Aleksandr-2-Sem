package app.api.controller;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.CategoryId;
import app.api.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ArticleService articleService;

  @Autowired
  private ObjectMapper objectMapper;

  private Article article;

  @BeforeEach
  void setUp() {
    article = new Article("Spring Boot", new ArticleId(1), "https://spring.io", new CategoryId(1));
  }

  @Test
  void testAddArticle() throws Exception {
    when(articleService.addArticle(any(Article.class))).thenReturn(article);

    mockMvc.perform(post("/articles/add_article")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(article)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Spring Boot"));
  }

  @Test
  void testGetArticle() throws Exception {
    when(articleService.findArticleById(1)).thenReturn(article);

    mockMvc.perform(get("/articles/get_article/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Spring Boot"));
  }

  @Test
  void testUpdateArticle() throws Exception {
    when(articleService.updateArticle(any(Article.class))).thenReturn(article);

    mockMvc.perform(put("/articles/put_article")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(article)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Spring Boot"));
  }

  @Test
  void testPatchArticle() throws Exception {
    when(articleService.patchArticle(any(Article.class))).thenReturn(article);

    mockMvc.perform(patch("/articles/patch_article")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(article)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Spring Boot"));
  }

  @Test
  void testDeleteArticle() throws Exception {
    when(articleService.deleteArticle(1)).thenReturn(article);

    mockMvc.perform(delete("/articles/delete_article/1"))
        .andExpect(status().isOk());
  }
}
