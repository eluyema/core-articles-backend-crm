CREATE TABLE articles (
                          id UUID PRIMARY KEY,
                          slug VARCHAR(255) NOT NULL UNIQUE,
                          author_id BIGINT NOT NULL,
                          CONSTRAINT fk_article_author FOREIGN KEY (author_id)
                              REFERENCES users(id)
                              ON DELETE CASCADE,
                          created_at TIMESTAMP NOT NULL DEFAULT now(),
                          updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE article_translations (
                                      id UUID PRIMARY KEY,
                                      article_id UUID NOT NULL,
                                      language VARCHAR(10) NOT NULL,
                                      title TEXT NOT NULL,
                                      description TEXT NOT NULL,
                                      preview_image_url TEXT,
                                      preview_blur_image_data_url TEXT,
                                      preview_image_alt TEXT,
                                      content JSONB NOT NULL,
                                      created_at TIMESTAMP NOT NULL DEFAULT now(),
                                      updated_at TIMESTAMP NOT NULL DEFAULT now(),
                                      CONSTRAINT fk_article FOREIGN KEY (article_id)
                                          REFERENCES articles(id)
                                          ON DELETE CASCADE,
                                      CONSTRAINT unique_article_language UNIQUE (article_id, language)
);

CREATE TABLE christianity_articles (
                                       id UUID PRIMARY KEY,
                                       article_id UUID NOT NULL UNIQUE,
                                       subcategory_id BIGINT NOT NULL,
                                       CONSTRAINT fk_subcategory FOREIGN KEY (subcategory_id)
                                           REFERENCES christianity_subcategory(id)
                                           ON DELETE CASCADE,
                                       CONSTRAINT fk_christianity_article FOREIGN KEY (article_id)
                                           REFERENCES articles(id)
                                           ON DELETE CASCADE
);
