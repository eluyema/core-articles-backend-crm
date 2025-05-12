package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.article.entities.ArticleTranslation;
import com.articles.crm.modules.christianity.dtos.UpdateChristianityArticleTranslation;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import com.articles.crm.modules.image.entities.ArticleImage;
import com.articles.crm.modules.image.services.ArticleImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UpdateChristianArticleTranslationUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;
    private final ArticleImageRepository articleImageRepository;

    public UpdateChristianArticleTranslationUseCase(ChristianityArticleRepository christianityArticleRepository,
                                                    ArticleImageRepository articleImageRepository
                                                    ) {
        this.christianityArticleRepository = christianityArticleRepository;
        this.articleImageRepository = articleImageRepository;
    }

    @Transactional
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
        articleImageRepository.detachImagesFromArticle(updatedTranslation.getId());
        List<ArticleImage> images = articleImageRepository.findByImageUrls(translation.getAddedImageUrls());
        images.forEach(img->img.setArticleTranslation(updatedTranslation));
        updatedTranslation.setImages(images);
        updatedTranslation.addContent(translation.getContent());

        christianityArticleRepository.save(christianityArticle);
    }
}
