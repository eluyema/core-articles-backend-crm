package com.articles.crm.modules.verses.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VersePostTranslationInfo {
    private String slug;
    private String lang;

    private String verseText;

    private Integer book;
    private Integer chapter;
    private Integer verse;

    private String content;
    private Object metadata;
    private String createdAt;
    private String updatedAt;
}
