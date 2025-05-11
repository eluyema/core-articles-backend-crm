package com.articles.crm.modules.christianity.services;

import com.articles.crm.modules.christianity.entities.ChristianitySubcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChristianitySubcategoryRepository extends JpaRepository<ChristianitySubcategory, Long> {
    public Optional<ChristianitySubcategory> findByCode(String code);
}