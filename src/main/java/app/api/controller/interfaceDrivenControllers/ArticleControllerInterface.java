package app.api.controller.interfaceDrivenControllers;

import app.api.entity.Article;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Tag(name = "ArticleControllerInterface", description = "Методы для работы со статьями")
@RequestMapping("/articles")
public interface ArticleControllerInterface {

  @Operation(summary = "Добавить стаью")
  @ApiResponse(responseCode = "200", description = "Статья добавлена")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @PostMapping("/add_article")
  ResponseEntity<Article> addArticle(
      @Parameter(description = "Статья", required = true)
      @RequestBody Article article
  );

  @Operation(summary = "Удалить стаью")
  @ApiResponse(responseCode = "200", description = "Статья удалена")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @DeleteMapping("/delete_article/{id}")
  ResponseEntity<?> deleteArticle(
      @Parameter(description = "Id статьи", required = true)
      @PathVariable Long id
  );

  @Operation(summary = "Найти стаью по id")
  @ApiResponse(responseCode = "200", description = "Статья найдена")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @GetMapping("/get_article/{id}")
  ResponseEntity<Article> getArticle(
      @Parameter(description = "Id статьи", required = true)
      @PathVariable Long id
  );

  @Operation(summary = "Заменить стаью")
  @ApiResponse(responseCode = "200", description = "Статья заменена")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @PutMapping("/put_article")
  ResponseEntity<Article> putArticle(
      @Parameter(description = "Статья", required = true)
      @RequestBody Article article
  );
}
