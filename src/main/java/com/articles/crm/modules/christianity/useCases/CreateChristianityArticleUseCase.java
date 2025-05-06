package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.article.entities.ArticleTranslation;
import com.articles.crm.modules.article.entities.ArticleTranslationContent;
import com.articles.crm.modules.christianity.dtos.CreateChristianityArticle;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.entities.ChristianitySubcategory;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import com.articles.crm.modules.christianity.services.ChristianitySubcategoryRepository;
import com.articles.crm.modules.user.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateChristianityArticleUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;
    private final ChristianitySubcategoryRepository christianitySubcategoryRepository;

    public CreateChristianityArticleUseCase(ChristianityArticleRepository christianityArticleRepository, ChristianitySubcategoryRepository christianitySubcategoryRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
        this.christianitySubcategoryRepository = christianitySubcategoryRepository;
    }

    public ChristianityArticle handle(User author, CreateChristianityArticle dto) throws Exception {
        Article article = new Article();
        article.setId(UUID.randomUUID());
        article.setAuthor(author);
        article.setSlug(dto.getSlug());

        ArticleTranslation translation = new ArticleTranslation();
        translation.setId(UUID.randomUUID());
        translation.setArticle(article);
        translation.setLanguage(dto.getLanguage());
        translation.setTitle(dto.getTitle());
        translation.setDescription(dto.getDescription());
        translation.setPreviewImageUrl(dto.getPreviewImageUrl());
        translation.setPreviewImageAlt(dto.getPreviewImageAlt());
        translation.setPreviewBlurImageImageUrl(dto.getPreviewBlurImageImageUrl());

        var content = new ArticleTranslationContent();

        content.setId(UUID.randomUUID());
        content.setContent(dto.getContent());
        content.setArticleTranslation(translation);

        translation.setContent(content);

        article.getTranslations().add(translation);

        ChristianityArticle christianityArticle = new ChristianityArticle();
        ChristianitySubcategory christianitySubcategory = christianitySubcategoryRepository.findById(dto.getSubcategoryId()).orElse(null);

        if(christianitySubcategory == null) {
            throw new Exception("ChristianitySubcategory hadn't found");
        }

        christianityArticle.setSubcategory(christianitySubcategoryRepository.findById(dto.getSubcategoryId()).orElse(null));
        christianityArticle.setArticle(article);
        christianityArticle.setId(UUID.randomUUID());

        return christianityArticleRepository.save(christianityArticle);
    }
}
