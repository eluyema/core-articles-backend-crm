package com.articles.crm.modules.christianity.services;

import com.articles.crm.modules.christianity.dbProjections.DbChristianArticlePath;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChristianityArticleRepository extends JpaRepository<ChristianityArticle, Long> {
    @Query("""
    SELECT new com.articles.crm.modules.christianity.dbProjections.DbChristianArticlePath(
        art.language,
        a.slug,
        cc.code,
        cs.code
    )
    FROM ChristianityArticle ca
    JOIN ca.article a
    JOIN ca.subcategory cs
    JOIN cs.category cc
    JOIN a.translations art
""")
    List<DbChristianArticlePath> fetchArticlePaths();

    Optional<ChristianityArticle> findByArticle_Slug(String slug);

    void deleteByArticle_Slug(String slug);
}
