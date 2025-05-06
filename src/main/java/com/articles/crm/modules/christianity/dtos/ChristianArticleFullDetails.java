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
public class ChristianArticleFullDetails {
    private UUID id;
    private String categoryCode;
    private String subcategoryCode;
    private String slug;
    private UserDetailsDto author;
    private String updatedAt;
    private List<ArticleTranslationDetails> translations = new ArrayList<>();

    public static ChristianArticleFullDetails fromEntity(ChristianityArticle christianityArticle) {
        ChristianArticleFullDetails articleDTO = new ChristianArticleFullDetails();

        articleDTO.setId(christianityArticle.getId());
        articleDTO.setCategoryCode(christianityArticle.getSubcategory().getCode());
        articleDTO.setSubcategoryCode(christianityArticle.getSubcategory().getCategory().getCode());

        Article article = christianityArticle.getArticle();

        UserDetailsDto userDetailsDto = new UserDetailsDto();

        userDetailsDto.setEmail(article.getAuthor().getEmail());
        userDetailsDto.setUsername(article.getAuthor().getUsername());
        userDetailsDto.setRoles(article.getAuthor().getRole().name());

        articleDTO.setAuthor(userDetailsDto);

        articleDTO.setSlug(article.getSlug());
        articleDTO.setUpdatedAt(article.getUpdatedAt().toString());

        articleDTO.setTranslations(article.getTranslations().stream().map(translation -> {
            var translationDTO = new ArticleTranslationDetails();
            translationDTO.setId(translation.getId());
            translationDTO.setTitle(translation.getTitle());
            translationDTO.setLanguage(translation.getLanguage());
            translationDTO.setDescription(translation.getDescription());
            translationDTO.setPreviewImageAlt(translation.getPreviewImageAlt());
            translationDTO.setPreviewBlurImageImageUrl(translation.getPreviewBlurImageImageUrl());
            translationDTO.setPreviewImageUrl(translation.getPreviewImageUrl());
            translationDTO.setContent(translation.getContent().getContent());
            return translationDTO;
        }).toList());

        return articleDTO;
    }
}
