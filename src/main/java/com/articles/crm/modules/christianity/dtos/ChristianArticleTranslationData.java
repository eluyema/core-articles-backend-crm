package com.articles.crm.modules.christianity.dtos;

import com.articles.crm.modules.article.dtos.ArticleTranslationDetails;
import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.user.dto.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChristianArticleTranslationData {
    private UUID id;
    private String category;
    private String subcategory;
    private String slug;
    private String createdAt;
    private String updatedAt;
    private List<String> availableLanguages;
    private String language;
    private String title;
    private String description;
    private String previewImageUrl;
    private String previewImageAlt;
    private String previewBlurImageImageUrl;
    private String content;

    public static ChristianArticleTranslationData fromEntity(ChristianityArticle christianityArticle, List<String> availableLanguages) {
        ChristianArticleTranslationData articleDTO = new ChristianArticleTranslationData();

        articleDTO.setId(christianityArticle.getId());
        articleDTO.setCategory(christianityArticle.getSubcategory().getCategory().getCode());
        articleDTO.setSubcategory(christianityArticle.getSubcategory().getCode());

        Article article = christianityArticle.getArticle();

        articleDTO.setSlug(article.getSlug());
        articleDTO.setUpdatedAt(article.getUpdatedAt().toString());
        // here should be always one translation of selected language
        article.getTranslations().stream().findFirst().ifPresent(translation -> {
            articleDTO.setTitle(translation.getTitle());
            articleDTO.setLanguage(translation.getLanguage());
            articleDTO.setDescription(translation.getDescription());
            articleDTO.setPreviewImageAlt(translation.getPreviewImageAlt());
            articleDTO.setPreviewBlurImageImageUrl(translation.getPreviewBlurImageImageUrl());
            articleDTO.setPreviewImageUrl(translation.getPreviewImageUrl());
            articleDTO.setContent(translation.getContent().getContent());
        });
        articleDTO.setAvailableLanguages(availableLanguages);

        return articleDTO;
    }
}
