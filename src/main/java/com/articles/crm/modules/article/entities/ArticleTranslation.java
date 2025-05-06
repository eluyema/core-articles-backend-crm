package com.articles.crm.modules.article.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
        name = "article_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"article_id", "language"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTranslation {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(length = 10, nullable = false)
    private String language;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "preview_image_url", columnDefinition = "TEXT")
    private String previewImageUrl;

    @Column(name = "preview_blur_image_data_url", columnDefinition = "TEXT")
    private String previewBlurImageImageUrl;

    @Column(name = "preview_image_alt", columnDefinition = "TEXT")
    private String previewImageAlt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "content_id", nullable = false, unique = true)
    private ArticleTranslationContent content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void addContent(String content) {
        ArticleTranslationContent articleTranslationContent = new ArticleTranslationContent();
        articleTranslationContent.setArticleTranslation(this);
        articleTranslationContent.setContent(content);

        this.content = articleTranslationContent;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}