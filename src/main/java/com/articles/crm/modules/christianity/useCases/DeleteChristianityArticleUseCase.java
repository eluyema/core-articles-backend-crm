package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteChristianityArticleUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;

    public DeleteChristianityArticleUseCase (ChristianityArticleRepository christianityArticleRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
    }

    @Transactional
    public void handle(String slug) {
        christianityArticleRepository.deleteByArticle_Slug(slug);
    }
}
