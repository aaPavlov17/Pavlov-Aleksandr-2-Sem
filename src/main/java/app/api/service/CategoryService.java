package app.api.service;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.repository.CategoryRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryService {

  private CategoryRepositoryImpl categoryRepository;

  @Autowired
  public CategoryService(CategoryRepositoryImpl categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category addCategory(Category category) {
    Category addedCategory = categoryRepository.addCategory(category);
    if (addedCategory != null) {
      log.info("Category {} has been added", addedCategory.getName());
    } else {
      log.info("Category {} already exists", category.getName());
    }
    return addedCategory;
  }

  public Category updateCategory(Category category) {
    Category updatedCategory = categoryRepository.updateCategory(category);
    if (updatedCategory != null) {
      log.info("Category {} has been updated", updatedCategory.getName());
    } else {
      log.info("Category {} does not exist", category.getName());
    }
    return updatedCategory;
  }

  public Category deleteCategory(int id) {
    Category deletedCategory = categoryRepository.deleteCategory(id);
    if (deletedCategory != null) {
      log.info("Category {} has been deleted", deletedCategory.getName());
    } else {
      log.info("Category with id {} does not exist", id);
    }
    return deletedCategory;
  }

  public Category getCategoryById(int id) {
    Category category = categoryRepository.findCategoryById(id);
    if (category != null) {
      log.info("Category with id {} has been found", id);
    } else {
      log.info("Category with id {} does not exist", id);
    }
    return category;
  }

  public Category patchCategory(Category category) {
    Category updatedCategory = categoryRepository.patchCategory(category);
    if (updatedCategory != null) {
      log.info("Category with id {} has been patched", category.getId().getId());
    } else {
      log.info("Category with id {} does not exist", category.getId().getId());
    }
    return updatedCategory;
  }
}
