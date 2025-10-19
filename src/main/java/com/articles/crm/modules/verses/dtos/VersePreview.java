package com.articles.crm.modules.verses.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VersePreview {
    private String slug;
    private String language;
    private String content;
    private Integer book;
    private Integer chapter;
    private Integer verse;
    private String createdAt;
    private String updatedAt;

    public VersePreview(String slug, String lang, String content,
                        Integer book, Integer chapter, Integer verse,
                        String createdAt, String updatedAt) {
        this.slug = slug;
        this.language = lang;
        this.content = content;
        this.book = book;
        this.chapter = chapter;
        this.verse = verse;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
