
CREATE TABLE article_translation_content (
                                             id UUID PRIMARY KEY,
                                             article_translation_id UUID NOT NULL,
                                             content JSONB NOT NULL,
                                             created_at TIMESTAMP NOT NULL DEFAULT now(),
                                             updated_at TIMESTAMP NOT NULL DEFAULT now(),
                                             CONSTRAINT fk_article_translation FOREIGN KEY (article_translation_id)
                                                 REFERENCES article_translations(id)
                                                 ON DELETE CASCADE
);


ALTER TABLE article_translations
DROP COLUMN content;


ALTER TABLE article_translations
add column content_id UUID NOT NULL UNIQUE;

ALTER TABLE article_translations
    ADD CONSTRAINT fk_article_translation_content FOREIGN KEY (content_id)
        REFERENCES article_translation_content(id)
        ON DELETE CASCADE;
