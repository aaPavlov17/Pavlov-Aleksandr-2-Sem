package app.api.controller;

import app.api.controller.interfaceDrivenControllers.CategoryControllerInterface;
import app.api.entity.Category;
import app.api.service.CategoryService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CategoryController implements CategoryControllerInterface {

  private final CategoryService categoryService;
  private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("categoryServiceCircuitBreaker");

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Override
  public ResponseEntity<Category> getCategoryById(Long id) {
    log.info("Getting category");
    return circuitBreaker.executeSupplier(() -> {
      try {
        Category category = categoryService.getCategoryById(id);
        log.info("Category {} found", category);
        return new ResponseEntity<>(category, HttpStatus.OK);
      } catch (Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    });
  }

  @Override
  public ResponseEntity<Category> addCategory(Category category) {
    log.info("Adding category");
    return circuitBreaker.executeSupplier(() -> {
      try {
        Category addedCategory = categoryService.addCategory(category);
        log.info("Category {} added", category.getName());
        return new ResponseEntity<>(addedCategory, HttpStatus.OK);
      } catch (Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    });
  }

  @Override
  public ResponseEntity<?> deleteCategory(Long id) {
    log.info("Deleting category");
    return circuitBreaker.executeSupplier(() -> {
      try {
        categoryService.deleteCategory(id);
        log.info("Category deleted");
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    });
  }

  @Override
  public ResponseEntity<Category> putCategory(Category category) {
    log.info("Updating category");
    return circuitBreaker.executeSupplier(() -> {
      try {
        Category updatedCategory = categoryService.updateCategory(category);
        log.info("Category {} updated", updatedCategory.getName());
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
      } catch (Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    });
  }
}
