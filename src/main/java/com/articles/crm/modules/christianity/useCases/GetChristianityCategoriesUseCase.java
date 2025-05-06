package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.christianity.entities.ChristianityCategory;
import com.articles.crm.modules.christianity.services.ChristianityCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetChristianityCategoriesUseCase {
    private final ChristianityCategoryRepository categoryRepository;

    public GetChristianityCategoriesUseCase(ChristianityCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ChristianityCategory> handle() {
        return categoryRepository.findAll();
    }


}
