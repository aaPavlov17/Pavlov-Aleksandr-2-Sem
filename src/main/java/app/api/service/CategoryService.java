package app.api.service;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.repository.CategoryRepository;
import jakarta.transaction.Transactional;
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
public class CategoryService {

  private CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Transactional
  public Category addCategory(Category category) {
    return categoryRepository.save(category);
  }

  @Transactional
  public Category updateCategory(Category category) {
    categoryRepository.deleteById(category.getId());
    return categoryRepository.save(category);
  }

  @Transactional
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(new CategoryId(id));
  }

  @Transactional
  public Category getCategoryById(Long id) {
    return categoryRepository.findById(new CategoryId(id)).orElse(null);
  }
}
