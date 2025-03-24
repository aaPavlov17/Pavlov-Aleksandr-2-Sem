package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Data
public class CategoryRepositoryImpl implements CategoryRepository {

  private List<Category> categories;

  public CategoryRepositoryImpl() {
    categories = new ArrayList<>();
  }

  @Override
  public Category addCategory(Category category) {
   for (Category c : categories) {
     if (c.getId().equals(category.getId())) {
       return null;
     }
   }
   categories.add(category);
   return category;
  }

  @Override
  public Category updateCategory(Category newCategory) {
    for (Category category : categories) {
      if (category.getId().equals(newCategory.getId())) {
        categories.set(categories.indexOf(newCategory), newCategory);
        return category;
      }
    }
    return null;
  }

  @Override
  public Category deleteCategory(int id) {
    CategoryId categoryId = new CategoryId(id);
    categories.removeIf(category -> category.getId().equals(categoryId));
    return null;
  }

  @Override
  public Category findCategoryById(int id) {
    CategoryId categoryId = new CategoryId(id);
    for (Category category : categories) {
      if (category.getId().equals(categoryId)) {
        return category;
      }
    }
    return null;
  }

  @Override
  public Category patchCategory(Category category) {
    for (Category c : categories) {
      if (c.getId().equals(category.getId())) {
        categories.set(categories.indexOf(c), category);
        return category;
      }
    }
    return null;
  }
}
