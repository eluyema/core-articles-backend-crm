package com.articles.crm.modules.verses.controllers;

import com.articles.crm.modules.christianity.dtos.*;
import com.articles.crm.modules.christianity.useCases.*;
import com.articles.crm.modules.user.entities.User;
import com.articles.crm.modules.verses.useCases.AddVersePostUseCase;
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

    public VersePostController(AddVersePostUseCase addVersePostUseCase) {
        this.addVersePostUseCase = addVersePostUseCase;
    }

    @PostMapping("/generate")
    public ResponseEntity<Object> createArticle() throws Exception {
        addVersePostUseCase.handle();
        return ResponseEntity.ok().build();
    }
}
