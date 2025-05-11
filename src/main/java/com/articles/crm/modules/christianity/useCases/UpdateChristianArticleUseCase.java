package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.article.entities.ArticleTranslation;
import com.articles.crm.modules.christianity.dtos.UpdateChristianityArticle;
import com.articles.crm.modules.christianity.dtos.UpdateChristianityArticleTranslation;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.entities.ChristianitySubcategory;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import com.articles.crm.modules.christianity.services.ChristianitySubcategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateChristianArticleUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;
    private final ChristianitySubcategoryRepository christianitySubcategoryRepository;

    public UpdateChristianArticleUseCase(ChristianityArticleRepository christianityArticleRepository,
                                         ChristianitySubcategoryRepository christianitySubcategoryRepository
                                         ) {
        this.christianityArticleRepository = christianityArticleRepository;
        this.christianitySubcategoryRepository = christianitySubcategoryRepository;
    }
    public void handle(String slug, UpdateChristianityArticle updatedData) throws Exception {
        ChristianityArticle christianityArticle = christianityArticleRepository.findByArticle_Slug(slug).orElseThrow(
                () -> new Exception("Article not found with slug: " + slug ));

        Article article = christianityArticle.getArticle();

        if(updatedData.getSlug() != null && !updatedData.getSlug().equals(article.getSlug())) {
            article.setSlug(updatedData.getSlug());
        }

        if(updatedData.getActive() != null && !updatedData.getActive().equals(christianityArticle.getActive())) {
            christianityArticle.setActive(updatedData.getActive());
        }

        if(updatedData.getSubcategory() != null && !updatedData.getSubcategory().equals(christianityArticle.getSubcategory().getCode()) ) {
            ChristianitySubcategory subcategory = christianitySubcategoryRepository.findByCode(updatedData.getSubcategory()).orElseThrow(
                    () -> new Exception("Article Subcategory not found with code: " + updatedData.getSubcategory()));

            christianityArticle.setSubcategory(subcategory);
        }

        christianityArticleRepository.save(christianityArticle);
    }
}
