ALTER TABLE christianity_subcategory
DROP CONSTRAINT fk_category;

ALTER TABLE christianity_subcategory
    ADD CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES christianity_category(id)
            ON DELETE CASCADE;