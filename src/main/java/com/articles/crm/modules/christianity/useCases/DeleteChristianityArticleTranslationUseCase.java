package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteChristianityArticleTranslationUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;

    public DeleteChristianityArticleTranslationUseCase(ChristianityArticleRepository christianityArticleRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
    }

    @Transactional
    public void handle(String slug, String language) {
        ChristianityArticle article = christianityArticleRepository.findByArticle_Slug(slug).orElseThrow(() -> new RuntimeException("Article not found"));

        article.getArticle().getTranslations().removeIf(translation -> translation.getLanguage().equals(language));

        christianityArticleRepository.save(article);
    }
}
