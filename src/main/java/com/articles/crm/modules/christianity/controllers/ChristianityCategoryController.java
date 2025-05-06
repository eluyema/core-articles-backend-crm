package com.articles.crm.modules.christianity.controllers;

import com.articles.crm.modules.christianity.entities.ChristianityCategory;
import com.articles.crm.modules.christianity.useCases.GetChristianityCategoriesUseCase;
import com.articles.crm.modules.christianity.useCases.UpdateAllChristianityCategoriesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/api/christianity/categories", produces = "application/json")
@RequiredArgsConstructor
public class ChristianityCategoryController {
    private final GetChristianityCategoriesUseCase getChristianityCategoriesUseCase;
    private final UpdateAllChristianityCategoriesUseCase updateAllCategoriesUseCase;

    @GetMapping
    public List<ChristianityCategory> findAll() {
        return getChristianityCategoriesUseCase.handle();
    }

    @PutMapping("/bulk")
    public List<ChristianityCategory> replaceAll(@RequestBody List<ChristianityCategory> categories) {
        return updateAllCategoriesUseCase.handle(categories);
    }
}
