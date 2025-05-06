package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.christianity.dtos.ChristianArticlePath;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetChristianityArticlePathsUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;
    public GetChristianityArticlePathsUseCase(ChristianityArticleRepository christianityArticleRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
    }

    public List<ChristianArticlePath> handle() {
        var dbPaths = christianityArticleRepository.fetchArticlePaths();

        return dbPaths.stream().map(path ->
                new ChristianArticlePath(path.getLanguage(), path.getSlug(), path.getCategory(), path.getSubcategory())).toList();
    }
}
