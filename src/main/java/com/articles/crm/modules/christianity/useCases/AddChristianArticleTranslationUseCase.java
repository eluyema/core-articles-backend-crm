package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.article.entities.ArticleTranslation;
import com.articles.crm.modules.christianity.dtos.CreateChristianityArticleTranslation;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class AddChristianArticleTranslationUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;

    public AddChristianArticleTranslationUseCase(ChristianityArticleRepository christianityArticleRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
    }

    public void handle(String slug, String lang, CreateChristianityArticleTranslation translation) throws Exception {
        ChristianityArticle christianityArticle = christianityArticleRepository.findByArticle_Slug(slug).orElseThrow(() -> new Exception("Article not found with slug: " + slug + " and language: " + lang));

        Article article = christianityArticle.getArticle();

        ArticleTranslation newTranslation = new ArticleTranslation();

        newTranslation.setArticle(article);
        newTranslation.setLanguage(lang);
        newTranslation.setTitle(translation.getTitle());
        newTranslation.setDescription(translation.getDescription());
        newTranslation.setPreviewImageUrl(translation.getPreviewImageUrl());
        newTranslation.setPreviewImageAlt(translation.getPreviewImageAlt());
        newTranslation.setPreviewBlurImageImageUrl(translation.getPreviewBlurImageImageUrl());

        newTranslation.addContent(translation.getContent());

        article.addTranslation(newTranslation);

        christianityArticleRepository.save(christianityArticle);
    }
}
