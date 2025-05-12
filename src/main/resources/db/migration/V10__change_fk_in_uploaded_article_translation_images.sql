ALTER TABLE uploaded_article_images
DROP CONSTRAINT fk_uploaded_article_images_article;

ALTER TABLE uploaded_article_images
    RENAME COLUMN article_id TO article_translation_id;

ALTER TABLE uploaded_article_images
    RENAME TO uploaded_article_translation_images;

ALTER TABLE uploaded_article_translation_images
    ADD CONSTRAINT fk_uploaded_article_translation_images_article_translation_id
        FOREIGN KEY (article_translation_id)
            REFERENCES article_translations(id)
            ON DELETE SET NULL;
