package app.api.controller.interfaceDrivenControllers;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CategoryControllerInterface", description = "Методы для работы с категориями")
@RequestMapping("/categories")
public interface CategoryControllerInterface {

  @Operation(summary = "Получить категорию по id")
  @ApiResponse(responseCode = "200", description = "Категория найдена")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @ApiResponse(responseCode = "404", description = "Категория не найдена")
  @GetMapping("/get_category/{id}")
  ResponseEntity<Category> getCategoryById(
      @Parameter(description = "ID категории", required = true)
      @PathVariable Long id
  );

  @Operation(summary = "Добавить категорию")
  @ApiResponse(responseCode = "200", description = "Категория добавлена")
  @ApiResponse(responseCode = "400", description = "Некорректные данные")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @PostMapping("/add_category")
  ResponseEntity<Category> addCategory(
      @Parameter(description = "Категория", required = true)
      @RequestBody Category category
  );

  @Operation(summary = "Удалить категорию")
  @ApiResponse(responseCode = "200", description = "Категория удалена")
  @ApiResponse(responseCode = "404", description = "Категория не найдена")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @DeleteMapping("delete_category/{id}")
  ResponseEntity<?> deleteCategory(
      @Parameter(description = "ID категории", required = true)
      @PathVariable Long id
  );

  @Operation(summary = "Заменить категорию")
  @ApiResponse(responseCode = "200", description = "Категория заменена")
  @ApiResponse(responseCode = "404", description = "Категория не найдена")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @PutMapping("/put_category")
  ResponseEntity<Category> putCategory(
      @Parameter(description = "Категория", required = true)
      @RequestBody Category category
  );
}
