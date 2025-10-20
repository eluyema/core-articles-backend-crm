package com.articles.crm.modules.verses.controllers;

import com.articles.crm.modules.christianity.dtos.*;
import com.articles.crm.modules.christianity.useCases.*;
import com.articles.crm.modules.user.entities.User;
import com.articles.crm.modules.verses.dtos.VersePostTranslationInfo;
import com.articles.crm.modules.verses.dtos.VersePreview;
import com.articles.crm.modules.verses.useCases.AddVersePostUseCase;
import com.articles.crm.modules.verses.useCases.GetVersePostBySlugAndLangUseCase;
import com.articles.crm.modules.verses.useCases.GetVersePreviewListUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/christianity/verse-posts", produces = "application/json")
public class VersePostController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final AddVersePostUseCase addVersePostUseCase;
    private GetVersePreviewListUseCase getVersePreviewListUseCase;
    private GetVersePostBySlugAndLangUseCase getVersePostBySlugAndLangUseCase;

    public VersePostController(AddVersePostUseCase addVersePostUseCase,
                               GetVersePreviewListUseCase getVersePreviewListUseCase, GetVersePostBySlugAndLangUseCase getVersePostBySlugAndLangUseCase) {
        this.getVersePreviewListUseCase = getVersePreviewListUseCase;
        this.getVersePostBySlugAndLangUseCase = getVersePostBySlugAndLangUseCase;this.addVersePostUseCase = addVersePostUseCase;
    }

    @PostMapping("/generate")
    public ResponseEntity<Object> createArticle() throws Exception {
        addVersePostUseCase.handle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/translations/{slug}/{language}")
    public ResponseEntity<String> getVerseBySlugAndLang(@PathVariable final String slug, @PathVariable final String language) throws Exception {
        VersePostTranslationInfo info = this.getVersePostBySlugAndLangUseCase.handle(slug, language);
        return ResponseEntity.ok(mapper.writeValueAsString(info));
    }

    @GetMapping("/preview-list")
    public ResponseEntity<String> getPreviewList() throws Exception {
        List<VersePreview> paths = this.getVersePreviewListUseCase.handle();
        return ResponseEntity.ok(mapper.writeValueAsString(paths));
    }
}
