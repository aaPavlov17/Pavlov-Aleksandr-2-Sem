package app.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Category", description = "Сущность категории")
public class Category {

  @Schema(description = "Уникальный идентификатор", example = "123")
  private CategoryId id;

  @Schema(description = "Название категории", example = "ML")
  private String name;

}
