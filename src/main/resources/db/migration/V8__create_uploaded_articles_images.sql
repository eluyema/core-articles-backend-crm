CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE uploaded_article_images (
                                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                         image_url TEXT NOT NULL UNIQUE,
                                         created_at TIMESTAMP NOT NULL DEFAULT now(),
                                         article_id UUID,

                                         CONSTRAINT fk_uploaded_article_images_article
                                             FOREIGN KEY (article_id)
                                                 REFERENCES articles(id)
                                                 ON DELETE SET NULL
);
