package com.articles.crm.modules.verses.controllers;

import com.articles.crm.modules.verses.dtos.VersePreview;
import com.articles.crm.modules.verses.dtos.VersePostTranslationInfo;
import com.articles.crm.modules.verses.useCases.GetVersePreviewListUseCase;
import com.articles.crm.modules.verses.useCases.GetVersePostBySlugAndLangUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/christianity/client/verse-posts", produces = "application/json")
public class ClientVersePostController {
    private GetVersePreviewListUseCase getVersePreviewListUseCase;
    private GetVersePostBySlugAndLangUseCase getVersePostBySlugAndLangUseCase;
    private ObjectMapper mapper = new ObjectMapper();

    public ClientVersePostController(GetVersePreviewListUseCase getVersePreviewListUseCase, GetVersePostBySlugAndLangUseCase getVersePostBySlugAndLangUseCase) {
        this.getVersePreviewListUseCase = getVersePreviewListUseCase;
        this.getVersePostBySlugAndLangUseCase = getVersePostBySlugAndLangUseCase;
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
