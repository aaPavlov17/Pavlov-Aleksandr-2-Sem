package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, CategoryId> {

}
