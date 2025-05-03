package com.articles.crm.modules.christianity.controllers;

import com.articles.crm.modules.christianity.entities.ChristianityCategory;
import com.articles.crm.modules.christianity.services.ChristianityCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/christianity/categories")
@RequiredArgsConstructor
public class ChristianityCategoryController {
    private final ChristianityCategoryService categoryService;

    @PostMapping
    public ChristianityCategory create(@RequestBody ChristianityCategory category) {
        return categoryService.create(category);
    }

    @PutMapping("/{id}")
    public ChristianityCategory update(@PathVariable Long id, @RequestBody ChristianityCategory category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @GetMapping
    public List<ChristianityCategory> findAll() {
        return categoryService.findAll();
    }

    @PutMapping("/bulk")
    public List<ChristianityCategory> replaceAll(@RequestBody List<ChristianityCategory> categories) {
        return categoryService.updateAllCategories(categories);
    }
}
