package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.christianity.dtos.ChristianArticleDetails;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetChristianityArticlesRecommendationUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;

    public GetChristianityArticlesRecommendationUseCase(ChristianityArticleRepository christianityArticleRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
    }

    public List<ChristianArticleDetails> handle(String category, Integer limit) {
        List<ChristianityArticle> articles = christianityArticleRepository.findRandomByCategory(category, limit);

        return articles.stream().map(ChristianArticleDetails::fromEntity).toList();
    }
}
