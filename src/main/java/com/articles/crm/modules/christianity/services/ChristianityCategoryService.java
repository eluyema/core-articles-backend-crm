package com.articles.crm.modules.christianity.services;

import com.articles.crm.modules.christianity.entities.ChristianityCategory;
import com.articles.crm.modules.christianity.entities.ChristianitySubcategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChristianityCategoryService {
    private final ChristianityCategoryRepository categoryRepository;
    private final ChristianitySubcategoryRepository subcategoryRepository;

    public ChristianityCategory create(ChristianityCategory category) {
        return categoryRepository.save(category);
    }

    public ChristianityCategory update(Long id, ChristianityCategory updatedCategory) {
        ChristianityCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setCode(updatedCategory.getCode());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<ChristianityCategory> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public List<ChristianityCategory> updateAllCategories(List<ChristianityCategory> newCategories) {
        List<ChristianityCategory> existingCategories = categoryRepository.findAll();
        List<Long> receivedCategoryIds = newCategories.stream()
                .map(ChristianityCategory::getId)
                .filter(id -> id != null)
                .toList();
        List<ChristianityCategory> categoriesToDelete = existingCategories.stream()
                .filter(c -> c.getId() != null && !receivedCategoryIds.contains(c.getId()))
                .toList();
        categoryRepository.deleteAll(categoriesToDelete);

        List<ChristianityCategory> result = new java.util.ArrayList<>();

        for (ChristianityCategory newCategory : newCategories) {
            ChristianityCategory category;
            if (newCategory.getId() != null) {
                category = categoryRepository.findById(newCategory.getId()).orElse(new ChristianityCategory());
            } else {
                category = new ChristianityCategory();
            }

            category.setCode(newCategory.getCode());

            List<ChristianitySubcategory> processedSubcategories = new java.util.ArrayList<>();

            List<Long> receivedSubIds = newCategory.getSubcategories().stream()
                    .map(ChristianitySubcategory::getId)
                    .filter(id -> id != null)
                    .toList();

            for (ChristianitySubcategory newSub : newCategory.getSubcategories()) {
                ChristianitySubcategory sub;

                if (newSub.getId() != null) {
                    sub = subcategoryRepository.findById(newSub.getId())
                            .orElse(new ChristianitySubcategory());
                } else {
                    // Check by code to prevent duplicate creation
                    sub = subcategoryRepository.findAll().stream()
                            .filter(s -> s.getCode().equals(newSub.getCode()))
                            .findFirst()
                            .orElse(new ChristianitySubcategory());
                }

                sub.setCode(newSub.getCode());
                sub.setCategory(category);
                processedSubcategories.add(sub);
            }

            if (category.getSubcategories() == null) {
                category.setSubcategories(new ArrayList<>());
            }

            List<ChristianitySubcategory> toRemove = category.getSubcategories().stream()
                    .filter(s -> s.getId() != null && !receivedSubIds.contains(s.getId()))
                    .toList();
            category.getSubcategories().removeAll(toRemove);

            category.getSubcategories().clear();
            category.getSubcategories().addAll(processedSubcategories);

            ChristianityCategory savedCategory = categoryRepository.save(category);
            result.add(savedCategory);
        }

        return result;
    }



}
