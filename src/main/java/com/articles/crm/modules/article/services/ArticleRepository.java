package com.articles.crm.modules.article.services;

import com.articles.crm.modules.article.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
