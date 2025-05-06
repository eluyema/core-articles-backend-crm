package com.articles.crm.modules.article.services;

import com.articles.crm.modules.article.entities.ArticleTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTranslationRepository extends JpaRepository<ArticleTranslation, Long> {
}
