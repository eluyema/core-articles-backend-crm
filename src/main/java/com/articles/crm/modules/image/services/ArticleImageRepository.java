package com.articles.crm.modules.image.services;

import com.articles.crm.modules.image.entities.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, UUID> {
    boolean existsByImageUrl(String imageUrl);

    @Query("SELECT ai FROM ArticleImage ai WHERE ai.imageUrl IN :imageUrls")
    List<ArticleImage> findByImageUrls(@Param("imageUrls") List<String> imageUrls);

    @Modifying
    @Query("UPDATE ArticleImage ai SET ai.articleTranslation = null WHERE ai.articleTranslation.id = :articleTranslationId")
    void detachImagesFromArticle(UUID articleTranslationId);
}
