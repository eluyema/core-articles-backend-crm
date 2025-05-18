package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.christianity.dtos.ChristianArticleDetails;
import com.articles.crm.modules.christianity.dtos.ChristianArticlePath;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetChristianityArticlesUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;
    public GetChristianityArticlesUseCase(ChristianityArticleRepository christianityArticleRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
    }

    public List<ChristianArticleDetails> handle(String category, String subcategory) {
        List<ChristianityArticle> articles = null;

        if(subcategory != null) {
            articles =  christianityArticleRepository.findBySubcategory_Code(subcategory);
        } else if (category != null) {
            articles = christianityArticleRepository.findBySubcategory_Category_Code(category);
        } else {
            articles = christianityArticleRepository.findAll();
        }

        return articles.stream().map(ChristianArticleDetails::fromEntity).toList();
    }
}
