package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.article.entities.ArticleTranslation;
import com.articles.crm.modules.christianity.dtos.CreateChristianityArticleTranslation;
import com.articles.crm.modules.christianity.dtos.UpdateChristianityArticleTranslation;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateChristianArticleTranslationUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;

    public UpdateChristianArticleTranslationUseCase(ChristianityArticleRepository christianityArticleRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
    }
    public void handle(String slug, String lang, UpdateChristianityArticleTranslation translation) throws Exception {
        ChristianityArticle christianityArticle = christianityArticleRepository.findByArticle_Slug(slug).orElseThrow(
                () -> new Exception("Article not found with slug: " + slug + " and language: " + lang));

        Article article = christianityArticle.getArticle();

        ArticleTranslation updatedTranslation = article.getTranslations().stream().filter(t -> t.getLanguage().equalsIgnoreCase(lang)).findFirst()
                 .orElseThrow(() -> new Exception("Article translation not found with slug: " + slug + " and language: " + lang));

        updatedTranslation.setTitle(translation.getTitle());
        updatedTranslation.setDescription(translation.getDescription());
        updatedTranslation.setPreviewImageUrl(translation.getPreviewImageUrl());
        updatedTranslation.setPreviewImageAlt(translation.getPreviewImageAlt());
        updatedTranslation.setPreviewBlurImageImageUrl(translation.getPreviewBlurImageImageUrl());

        updatedTranslation.addContent(translation.getContent());

        christianityArticleRepository.save(christianityArticle);
    }
}
