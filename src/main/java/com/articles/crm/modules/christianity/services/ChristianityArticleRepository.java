package com.articles.crm.modules.christianity.services;

import com.articles.crm.modules.christianity.dbProjections.DbChristianArticlePath;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChristianityArticleRepository extends JpaRepository<ChristianityArticle, Long> {
    @Query("""
    SELECT new com.articles.crm.modules.christianity.dbProjections.DbChristianArticlePath(
        art.language,
        a.slug,
        cc.code,
        cs.code,
        ca.active,
        art.createdAt,
        art.updatedAt
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

    List<ChristianityArticle> findBySubcategory_Category_Code(String categoryCode);

    List<ChristianityArticle> findBySubcategory_Code(String subcategoryCode);

    @Query(value = """
        SELECT ca.*
        FROM christianity_articles ca
        JOIN articles a ON ca.article_id = a.id
        JOIN christianity_subcategory cs ON ca.subcategory_id = cs.id
        JOIN christianity_category cc ON cs.category_id = cc.id
        WHERE cc.code = :category and ca.active = true
        ORDER BY RANDOM()
        LIMIT :limit
""", nativeQuery = true)
    List<ChristianityArticle> findRandomByCategory(@Param("category") String category, @Param("limit") int limit);

}
