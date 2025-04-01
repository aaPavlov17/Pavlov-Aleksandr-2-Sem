CREATE TABLE articles
(
    name VARCHAR(255),
    url  VARCHAR(255),
    id   BIGINT NOT NULL,
    CONSTRAINT pk_articles PRIMARY KEY (id)
);

CREATE TABLE categories
(
    name VARCHAR(255),
    id   BIGINT NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE categories_of_article
(
    articles_id   BIGINT NOT NULL,
    categories_id BIGINT NOT NULL,
    CONSTRAINT pk_categories_of_article PRIMARY KEY (articles_id, categories_id)
);

ALTER TABLE categories_of_article
    ADD CONSTRAINT fk_catofart_on_article FOREIGN KEY (articles_id) REFERENCES articles (id);

ALTER TABLE categories_of_article
    ADD CONSTRAINT fk_catofart_on_category FOREIGN KEY (categories_id) REFERENCES categories (id);