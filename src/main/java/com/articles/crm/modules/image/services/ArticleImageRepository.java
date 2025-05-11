package com.articles.crm.modules.image.services;

import com.articles.crm.modules.image.entities.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, UUID> {
    boolean existsByImageUrl(String imageUrl);


    @Modifying
    @Query("UPDATE ArticleImage ai SET ai.articleId = :articleId WHERE ai.imageUrl IN :imageUrls")
    void updateArticleIdForUrls(List<String> imageUrls, UUID articleId);
}
