package com.articles.crm.modules.image.useCases;

import com.articles.crm.modules.image.services.ArticleImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AttachImagesToArticleUseCase {
    private final ArticleImageRepository repository;

    public AttachImagesToArticleUseCase(ArticleImageRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void handle(List<String> imageUrls, UUID articleId) {
        this.repository.updateArticleIdForUrls(imageUrls, articleId);
    }
}
