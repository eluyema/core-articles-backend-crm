package com.articles.crm.modules.article.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class ArticleTranslationDetails {
    private UUID id;
    private String language;
    private String title;
    private String description;
    private String previewImageUrl;
    private String previewImageAlt;
    private String previewBlurImageImageUrl;
    private String content;
}
