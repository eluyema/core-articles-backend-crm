package com.articles.crm.modules.christianity.services;

import com.articles.crm.modules.christianity.entities.ChristianityCategory;
import com.articles.crm.modules.christianity.entities.ChristianitySubcategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChristianitySubcategoryService {
    private final ChristianitySubcategoryRepository subcategoryRepository;
    private final ChristianityCategoryRepository categoryRepository;

    public ChristianitySubcategory create(Long categoryId, ChristianitySubcategory subcategory) {
        ChristianityCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        subcategory.setCategory(category);
        return subcategoryRepository.save(subcategory);
    }

    public ChristianitySubcategory update(Long id, ChristianitySubcategory updatedSubcategory) {
        ChristianitySubcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        subcategory.setCode(updatedSubcategory.getCode());
        return subcategoryRepository.save(subcategory);
    }

    public void delete(Long id) {
        subcategoryRepository.deleteById(id);
    }

    public List<ChristianitySubcategory> findAll() {
        return subcategoryRepository.findAll();
    }
}