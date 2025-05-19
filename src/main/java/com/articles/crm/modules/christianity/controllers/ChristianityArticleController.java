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
@RequestMapping(value = "/api/christianity", produces = "application/json")
public class ChristianityArticleController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final CreateChristianityArticleUseCase createChristianityArticleUseCase;
    private final DeleteChristianityArticleUseCase deleteChristianityArticleUseCase;
    private final GetChristianityArticleTranslationUseCase getChristianityArticleTranslationUseCase;
    private final GetChristianityArticlePathsUseCase getChristianityArticlePathsUseCase;
    private final GetChristianityArticlesUseCase getChristianityArticlesUseCase;
    private final DeleteChristianityArticleTranslationUseCase deleteChristianityArticleTranslationUseCase;
    private final GetFullChristianityArticleUseCase getFullChristianityArticleUseCase;
    private final AddChristianArticleTranslationUseCase addChristianArticleTranslationUseCase;
    private final UpdateChristianArticleTranslationUseCase updateChristianArticleTranslationUseCase;
    private final UpdateChristianArticleUseCase updateChristianArticleUseCase;
    private final TranslateArticleUseCase translateArticleUseCase;

    public ChristianityArticleController(
                                         CreateChristianityArticleUseCase createChristianityArticleUseCase,
                                         DeleteChristianityArticleUseCase deleteChristianityArticleUseCase,
                                         GetChristianityArticleTranslationUseCase getChristianityArticleTranslationUseCase,
                                         GetChristianityArticlePathsUseCase getChristianityArticlePathsUseCase,
                                         GetChristianityArticlesUseCase getChristianityArticlesUseCase,
                                         DeleteChristianityArticleTranslationUseCase deleteChristianityArticleTranslationUseCase,
                                         GetFullChristianityArticleUseCase getFullChristianityArticleUseCase,
                                         AddChristianArticleTranslationUseCase addChristianArticleTranslationUseCase,
                                         UpdateChristianArticleTranslationUseCase updateChristianArticleTranslationUseCase,
                                         UpdateChristianArticleUseCase updateChristianArticleUseCase,
                                         TranslateArticleUseCase translateArticleUseCase
                                         ) {
        this.createChristianityArticleUseCase =  createChristianityArticleUseCase;
        this.deleteChristianityArticleUseCase = deleteChristianityArticleUseCase;
        this.getChristianityArticleTranslationUseCase = getChristianityArticleTranslationUseCase;
        this.getChristianityArticlePathsUseCase = getChristianityArticlePathsUseCase;
        this.getChristianityArticlesUseCase = getChristianityArticlesUseCase;
        this.deleteChristianityArticleTranslationUseCase = deleteChristianityArticleTranslationUseCase;
        this.getFullChristianityArticleUseCase = getFullChristianityArticleUseCase;
        this.addChristianArticleTranslationUseCase = addChristianArticleTranslationUseCase;
        this.updateChristianArticleUseCase = updateChristianArticleUseCase;
        this.updateChristianArticleTranslationUseCase = updateChristianArticleTranslationUseCase;
        this.translateArticleUseCase = translateArticleUseCase;
    }

    @PostMapping("/articles")
    public ResponseEntity<Object> createArticle(@RequestBody final CreateChristianityArticle article) throws Exception {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        createChristianityArticleUseCase.handle(user, article);
        return ResponseEntity.ok().build();
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

    @PostMapping("/translations/{slug}/{language}")
    public ResponseEntity<String> addTranslation(@PathVariable final String slug, @PathVariable final String language,
                                                 @RequestBody final CreateChristianityArticleTranslation translation
                                                 ) throws Exception {
        addChristianArticleTranslationUseCase.handle(slug, language,translation);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/translations/{slug}/{language}")
    public ResponseEntity<String> updateTranslation(@PathVariable final String slug, @PathVariable final String language,
                                                 @RequestBody final UpdateChristianityArticleTranslation translation
    ) throws Exception {
        updateChristianArticleTranslationUseCase.handle(slug, language,translation);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/translations/{slug}/{language}/translateAll")
    public ResponseEntity<String> translateAll(@PathVariable final String slug, @PathVariable final String language) throws Exception {
        translateArticleUseCase.handle(slug, language);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/articles/{slug}")
    public ResponseEntity<String> getOneArticle(@PathVariable final String slug) throws Exception {
        ChristianArticleFullDetails article = getFullChristianityArticleUseCase.handle(slug);

        return ResponseEntity.ok(mapper.writeValueAsString(article));
    }

    @PutMapping("/articles/{slug}")
    public ResponseEntity<String> updateArticle(@PathVariable final String slug,
                                                    @RequestBody final UpdateChristianityArticle article
    ) throws Exception {
        updateChristianArticleUseCase.handle(slug, article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/articles/{slug}")
    public ResponseEntity<String> deleteArticleBySlug(@PathVariable final String slug) throws Exception {
        deleteChristianityArticleUseCase.handle(slug);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/translations/{slug}/{language}")
    public ResponseEntity<String> deleteTranslation(@PathVariable final String slug, @PathVariable final String language) throws Exception {
        deleteChristianityArticleTranslationUseCase.handle(slug, language);
        return ResponseEntity.ok().build();
    }
}
