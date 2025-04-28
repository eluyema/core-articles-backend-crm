package com.articles.crm.modules.christianity.controllers;

import com.articles.crm.modules.christianity.entities.ChristianitySubcategory;
import com.articles.crm.modules.christianity.services.ChristianitySubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/christianity/subcategories")
@RequiredArgsConstructor
public class ChristianitySubcategoryController {
    private final ChristianitySubcategoryService subcategoryService;

    @PostMapping("/{categoryId}")
    public ChristianitySubcategory create(@PathVariable Long categoryId, @RequestBody ChristianitySubcategory subcategory) {
        return subcategoryService.create(categoryId, subcategory);
    }

    @PutMapping("/{id}")
    public ChristianitySubcategory update(@PathVariable Long id, @RequestBody ChristianitySubcategory subcategory) {
        return subcategoryService.update(id, subcategory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        subcategoryService.delete(id);
    }

    @GetMapping
    public List<ChristianitySubcategory> findAll() {
        return subcategoryService.findAll();
    }
}