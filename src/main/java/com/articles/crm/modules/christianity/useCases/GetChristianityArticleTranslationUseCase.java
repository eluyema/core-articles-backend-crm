package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.christianity.dtos.ChristianArticleTranslationData;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetChristianityArticleTranslationUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;


    public  GetChristianityArticleTranslationUseCase ( ChristianityArticleRepository christianityArticleRepository){
        this.christianityArticleRepository = christianityArticleRepository;
    }

    public ChristianArticleTranslationData handle(String slug, String lang) throws Exception {
        ChristianityArticle christianityArticle = christianityArticleRepository.findByArticle_Slug(slug)
                .orElseThrow(() -> new Exception("Article not found with slug: " + slug + " and language: " + lang));
        ChristianArticleTranslationData articleDTO = new ChristianArticleTranslationData();

        articleDTO.setId(christianityArticle.getId());
        articleDTO.setCategory(christianityArticle.getSubcategory().getCategory().getCode());
        articleDTO.setSubcategory(christianityArticle.getSubcategory().getCode());

        Article article = christianityArticle.getArticle();

        articleDTO.setSlug(article.getSlug());
        articleDTO.setUpdatedAt(article.getUpdatedAt().toString());
        List<String> availableLanguages = new ArrayList<>();

        article.getTranslations().stream().forEach(translation -> {
            availableLanguages.add(translation.getLanguage());

            if(!translation.getLanguage().equals(lang)) {
                return;
            }

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
