package com.articles.crm.modules.image.entities;

import com.articles.crm.modules.article.entities.ArticleTranslation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "uploaded_article_translation_images")
@Setter
@Getter
public class ArticleImage {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "image_url", nullable = false, unique = true)
    private String imageUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "size", nullable = false)
    private long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_translation_id", nullable = true)
    private ArticleTranslation articleTranslation;

    public ArticleImage() {}

    public ArticleImage(String imageUrl,Long size) {
        this.imageUrl = imageUrl;
        this.size = size;
    }
}
