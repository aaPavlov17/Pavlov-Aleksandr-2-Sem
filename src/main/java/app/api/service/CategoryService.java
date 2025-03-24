package app.api.service;

import app.api.entity.Category;
import app.api.repository.CategoryRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@EnableCaching
public class CategoryService {

  private CategoryRepositoryImpl categoryRepository;

  private final ConcurrentHashMap<String, Boolean> executedOperations = new ConcurrentHashMap<>();

  @Autowired
  public CategoryService(CategoryRepositoryImpl categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @CachePut(value = "categories", key = "#category.id")
  public Category addCategory(Category category) {
    Category addedCategory = categoryRepository.addCategory(category);
    if (addedCategory != null) {
      log.info("Category {} has been added", addedCategory.getName());
    } else {
      log.info("Category {} already exists", category.getName());
    }
    return addedCategory;
  }

  @CachePut(value = "categories", key = "#category.id")
  public Category updateCategory(Category category) {
    Category updatedCategory = categoryRepository.updateCategory(category);
    if (updatedCategory != null) {
      log.info("Category {} has been updated", updatedCategory.getName());
    } else {
      log.info("Category {} does not exist", category.getName());
    }
    return updatedCategory;
  }

  @CacheEvict(value = "categories", key = "#id")
  public Category deleteCategory(int id) {
    Category deletedCategory = categoryRepository.deleteCategory(id);
    if (deletedCategory != null) {
      log.info("Category {} has been deleted", deletedCategory.getName());
    } else {
      log.info("Category with id {} does not exist", id);
    }
    return deletedCategory;
  }

  @Cacheable(value = "categories", key = "#id")
  public Category getCategoryById(int id) {
    Category category = categoryRepository.findCategoryById(id);
    if (category != null) {
      log.info("Category with id {} has been found", id);
    } else {
      log.info("Category with id {} does not exist", id);
    }
    return category;
  }

  @CachePut(value = "categories", key = "#category.id")
  public Category patchCategory(Category category) {
    Category updatedCategory = categoryRepository.patchCategory(category);
    if (updatedCategory != null) {
      log.info("Category with id {} has been patched", category.getId().getId());
    } else {
      log.info("Category with id {} does not exist", category.getId().getId());
    }
    return updatedCategory;
  }

  public void exactlyOnceOperationExample(String operationId) {
    if (executedOperations.putIfAbsent(operationId, true) != null) {
      log.warn("Операция {} уже была выполнена. Пропускаем.", operationId);
      return;
    }
    //логика
    log.info("Выполняем операцию {} ровно один раз", operationId);
  }
}
