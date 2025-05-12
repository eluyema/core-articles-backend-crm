package com.articles.crm.modules.christianity.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CreateChristianityArticle {
    private String subcategory;
    private String slug;
    private Boolean active;
    private String language;
    private String title;
    private String description;
    private String previewImageUrl;
    private String previewImageAlt;
    private String previewBlurImageImageUrl;
    private String content;
    private List<String> addedImageUrls;
}
