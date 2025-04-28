package com.articles.crm.modules.christianity.services;

import com.articles.crm.modules.christianity.entities.ChristianityCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChristianityCategoryService {
    private final ChristianityCategoryRepository categoryRepository;

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
}
