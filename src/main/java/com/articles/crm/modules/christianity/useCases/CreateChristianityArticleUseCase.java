package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.article.entities.ArticleTranslation;
import com.articles.crm.modules.article.entities.ArticleTranslationContent;
import com.articles.crm.modules.article.services.ArticleRepository;
import com.articles.crm.modules.article.services.ArticleTranslationRepository;
import com.articles.crm.modules.christianity.dtos.CreateChristianityArticle;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.entities.ChristianitySubcategory;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import com.articles.crm.modules.christianity.services.ChristianitySubcategoryRepository;
import com.articles.crm.modules.image.entities.ArticleImage;
import com.articles.crm.modules.image.services.ArticleImageRepository;
import com.articles.crm.modules.user.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateChristianityArticleUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;
    private final ChristianitySubcategoryRepository christianitySubcategoryRepository;
    private final ArticleImageRepository articleImageRepository;
    private final ArticleTranslationRepository articleTranslationRepository;
    private final ArticleRepository articleRepository;

    public CreateChristianityArticleUseCase(ChristianityArticleRepository christianityArticleRepository,
                                            ChristianitySubcategoryRepository christianitySubcategoryRepository,
                                            ArticleImageRepository articleImageRepository,
                                            ArticleTranslationRepository articleTranslationRepository,
                                            ArticleRepository articleRepository
                                            ) {
        this.christianityArticleRepository = christianityArticleRepository;
        this.christianitySubcategoryRepository = christianitySubcategoryRepository;
        this.articleImageRepository = articleImageRepository;
        this.articleTranslationRepository = articleTranslationRepository;
        this.articleRepository = articleRepository;
    }
    @Transactional
    public ChristianityArticle handle(User author, CreateChristianityArticle dto) throws Exception {
        Article article = new Article();
        article.setAuthor(author);
        article.setSlug(dto.getSlug());

        article = articleRepository.save(article);

        ArticleTranslation translation = new ArticleTranslation();
        translation.setArticle(article);
        translation.setLanguage(dto.getLanguage());
        translation.setTitle(dto.getTitle());
        translation.setDescription(dto.getDescription());
        translation.setPreviewImageUrl(dto.getPreviewImageUrl());
        translation.setPreviewImageAlt(dto.getPreviewImageAlt());
        translation.setPreviewBlurImageImageUrl(dto.getPreviewBlurImageImageUrl());

        var content = new ArticleTranslationContent();
        content.setContent(dto.getContent());
        content.setArticleTranslation(translation);
        translation.setContent(content);

        ArticleTranslation savedTranslation = articleTranslationRepository.save(translation);

        List<ArticleImage> images = articleImageRepository.findByImageUrls(dto.getAddedImageUrls());
        images.forEach(img -> img.setArticleTranslation(savedTranslation));
        articleImageRepository.saveAll(images);

        savedTranslation.setImages(images);

        article.getTranslations().add(savedTranslation);

        ChristianityArticle christianityArticle = new ChristianityArticle();
        ChristianitySubcategory christianitySubcategory = christianitySubcategoryRepository.findByCode(dto.getSubcategory())
                .orElseThrow(() -> new Exception("ChristianitySubcategory hadn't found"));

        christianityArticle.setSubcategory(christianitySubcategory);
        christianityArticle.setArticle(article);
        christianityArticle.setActive(dto.getActive());

        return christianityArticleRepository.save(christianityArticle);
    }

}
