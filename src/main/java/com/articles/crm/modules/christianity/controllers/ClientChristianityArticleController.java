package com.articles.crm.modules.christianity.controllers;

import com.articles.crm.modules.christianity.dtos.*;
import com.articles.crm.modules.christianity.useCases.*;
import com.articles.crm.modules.user.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/christianity/client", produces = "application/json")
public class ClientChristianityArticleController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final GetChristianityArticleTranslationUseCase getChristianityArticleTranslationUseCase;
    private final GetChristianityArticlePathsUseCase getChristianityArticlePathsUseCase;
    private final GetChristianityArticlesUseCase getChristianityArticlesUseCase;
    private final GetFullChristianityArticleUseCase getFullChristianityArticleUseCase;
    private final GetChristianityArticlesRecommendationUseCase getChristianityArticlesRecommendationUseCase;

    public ClientChristianityArticleController(
                                         GetChristianityArticleTranslationUseCase getChristianityArticleTranslationUseCase,
                                         GetChristianityArticlePathsUseCase getChristianityArticlePathsUseCase,
                                         GetChristianityArticlesUseCase getChristianityArticlesUseCase,
                                         GetFullChristianityArticleUseCase getFullChristianityArticleUseCase,
                                         GetChristianityArticlesRecommendationUseCase getChristianityArticlesRecommendationUseCase
                                         ) {
        this.getChristianityArticleTranslationUseCase = getChristianityArticleTranslationUseCase;
        this.getChristianityArticlePathsUseCase = getChristianityArticlePathsUseCase;
        this.getChristianityArticlesUseCase = getChristianityArticlesUseCase;
        this.getFullChristianityArticleUseCase = getFullChristianityArticleUseCase;
        this.getChristianityArticlesRecommendationUseCase = getChristianityArticlesRecommendationUseCase;
    }

    @GetMapping("/articles")
    public ResponseEntity<String> getAllArticles(@RequestParam(name = "category", required = false) String category, @RequestParam(name = "subcategory", required = false) String subcategory) throws Exception {
        List<ChristianArticleDetails> articleDTOs = getChristianityArticlesUseCase.handle(category, subcategory);

        return ResponseEntity.ok(mapper.writeValueAsString(articleDTOs));
    }

    @GetMapping("/articles-paths")
    public ResponseEntity<String> getAllPaths() throws Exception {
        List<ChristianArticlePath> paths = getChristianityArticlePathsUseCase.handle();
        return ResponseEntity.ok(mapper.writeValueAsString(paths));
    }



    @GetMapping("/translations/{slug}/{language}")
    public ResponseEntity<String> getOneArticleTranslation(@PathVariable final String slug, @PathVariable final String language) throws Exception {
        ChristianArticleTranslationData article = getChristianityArticleTranslationUseCase.handle(slug, language);

        return ResponseEntity.ok(mapper.writeValueAsString(article));
    }

    @GetMapping("/articles/{slug}")
    public ResponseEntity<String> getOneArticle(@PathVariable final String slug) throws Exception {
        ChristianArticleFullDetails article = getFullChristianityArticleUseCase.handle(slug);

        return ResponseEntity.ok(mapper.writeValueAsString(article));
    }

    @GetMapping("/article/recommendations")
    public ResponseEntity<String> getArticleRecommendations(
            @RequestParam(name = "category") String category,
            @RequestParam(name = "limit", required = false, defaultValue = "5") Integer limit
    ) throws Exception {
        List<ChristianArticleDetails> articleDTOs = getChristianityArticlesRecommendationUseCase.handle(category, limit);

        return ResponseEntity.ok(mapper.writeValueAsString(articleDTOs));
    }
}
