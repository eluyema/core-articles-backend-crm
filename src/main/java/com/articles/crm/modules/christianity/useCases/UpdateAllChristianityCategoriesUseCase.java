package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.christianity.entities.ChristianityCategory;
import com.articles.crm.modules.christianity.entities.ChristianitySubcategory;
import com.articles.crm.modules.christianity.services.ChristianityCategoryRepository;
import com.articles.crm.modules.christianity.services.ChristianitySubcategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateAllChristianityCategoriesUseCase {

    private final ChristianityCategoryRepository categoryRepository;

    // It can be avoided
    private final ChristianitySubcategoryRepository subcategoryRepository;

    public UpdateAllChristianityCategoriesUseCase(ChristianityCategoryRepository categoryRepository, ChristianitySubcategoryRepository subcategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Transactional
    public List<ChristianityCategory> handle(List<ChristianityCategory> newCategories) {
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
