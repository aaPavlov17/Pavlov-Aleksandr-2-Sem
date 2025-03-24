package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;

public interface CategoryRepository {

  Category addCategory(Category category);

  Category updateCategory(Category category);

  Category deleteCategory(int id);

  Category findCategoryById(int id);

  Category patchCategory(Category category);
}
