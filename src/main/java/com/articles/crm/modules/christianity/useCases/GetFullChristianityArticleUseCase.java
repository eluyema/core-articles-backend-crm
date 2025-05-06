package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.christianity.dtos.ChristianArticleFullDetails;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;

import org.springframework.stereotype.Service;

@Service
public class GetFullChristianityArticleUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;

    public GetFullChristianityArticleUseCase(ChristianityArticleRepository christianityArticleRepository) {
        this.christianityArticleRepository = christianityArticleRepository;

    }

    public ChristianArticleFullDetails handle(String slug) throws Exception {
        return ChristianArticleFullDetails.fromEntity(christianityArticleRepository.findByArticle_Slug(slug).orElseThrow(() -> new Exception("Article not found with slug: " + slug)));
    }
}
