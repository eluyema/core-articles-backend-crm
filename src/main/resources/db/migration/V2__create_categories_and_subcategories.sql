CREATE TABLE christianity_category (
   id BIGSERIAL PRIMARY KEY,
   code VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE christianity_subcategory (
   id BIGSERIAL PRIMARY KEY,
   code VARCHAR(255) NOT NULL UNIQUE,
   category_id BIGINT NOT NULL,
   CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES christianity_category(id)
);
