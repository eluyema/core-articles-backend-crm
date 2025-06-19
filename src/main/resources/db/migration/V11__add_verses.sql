CREATE TABLE verse_books (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             slug VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE verse_book_translations (
                                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         book_id UUID REFERENCES verse_books(id) ON DELETE CASCADE,
                                         language_code VARCHAR(10) NOT NULL,
                                         name VARCHAR(100) NOT NULL,
                                         UNIQUE (book_id, language_code)
);

CREATE TABLE verses (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              slug VARCHAR(255) UNIQUE NOT NULL,
                              book_id UUID REFERENCES verse_books(id) NOT NULL,
                              chapter INTEGER NOT NULL,
                              verse_number INTEGER NOT NULL,
                              created_at TIMESTAMP DEFAULT NOW(),
                              UNIQUE (book_id, chapter, verse_number)
);

CREATE TABLE verse_tags (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            slug VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE verse_tag_translations (
                                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                        tag_id UUID REFERENCES verse_tags(id) ON DELETE CASCADE,
                                        language_code VARCHAR(10) NOT NULL,
                                        name VARCHAR(255) NOT NULL,
                                        UNIQUE (tag_id, language_code)
);

CREATE TABLE verse_to_tags (
                               verse_id UUID REFERENCES verses(id) ON DELETE CASCADE,
                               tag_id UUID REFERENCES verse_tags(id) ON DELETE CASCADE,
                               PRIMARY KEY (verse_id, tag_id)
);

