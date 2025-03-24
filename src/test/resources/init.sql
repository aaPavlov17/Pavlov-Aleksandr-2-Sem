CREATE TABLE categories (
    id INT,
    name VARCHAR(255)
);

CREATE TABLE articles (
    name VARCHAR(255),
    id INT,
    url VARCHAR(255),
    category_id INT
);