package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.article.entities.ArticleTranslation;
import com.articles.crm.modules.article.services.ArticleTranslationRepository;
import com.articles.crm.modules.christianity.dtos.CreateChristianityArticleTranslation;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import com.articles.crm.modules.image.entities.ArticleImage;
import com.articles.crm.modules.image.services.ArticleImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddChristianArticleTranslationUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;
    private final ArticleImageRepository articleImageRepository;
    private final ArticleTranslationRepository articleTranslationRepository;

    public AddChristianArticleTranslationUseCase(ChristianityArticleRepository christianityArticleRepository,
                                                 ArticleImageRepository articleImageRepository,
                                                ArticleTranslationRepository articleTranslationRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
        this.articleImageRepository = articleImageRepository;
        this.articleTranslationRepository = articleTranslationRepository;
    }
    @Transactional
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
        ArticleTranslation savedTranslation = articleTranslationRepository.save(newTranslation);
        List<ArticleImage> images = articleImageRepository.findByImageUrls(translation.getAddedImageUrls());
        images.forEach(img -> img.setArticleTranslation(savedTranslation));
        articleImageRepository.saveAll(images);

        savedTranslation.setImages(images);

        christianityArticleRepository.save(christianityArticle);
    }
}
