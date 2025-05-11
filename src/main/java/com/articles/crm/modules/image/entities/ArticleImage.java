package com.articles.crm.modules.image.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "uploaded_article_images")
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

    @Column(name = "article_id")
    private UUID articleId;

    public ArticleImage() {}

    public ArticleImage(String imageUrl,Long size) {
        this.imageUrl = imageUrl;
        this.size = size;
    }
}
