package app.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Article", description = "Сущность пользователя")
public class Article {

    @Schema(description = "Имя Статьи", example = "ML")
    String name;

    @Schema(description = "Уникальный идентификатор", example = "123")
    ArticleId id;

    @Schema(description = "Url ссылка статьи", example = "https://habr.com/ru/articles/814061/")
    String url;

    @Schema(description = "Уникальный идентификатор категории статьи", example = "234")
    CategoryId categoryId;
}
