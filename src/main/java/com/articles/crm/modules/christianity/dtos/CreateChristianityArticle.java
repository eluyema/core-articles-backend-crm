package com.articles.crm.modules.christianity.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateChristianityArticle {
    private Long subcategoryId;
    private String slug;
    private String language;
    private String title;
    private String description;
    private String previewImageUrl;
    private String previewImageAlt;
    private String previewBlurImageImageUrl;
    private String content;
}
