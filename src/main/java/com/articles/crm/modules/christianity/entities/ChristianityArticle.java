package com.articles.crm.modules.christianity.entities;

import com.articles.crm.modules.article.entities.Article;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "christianity_articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChristianityArticle {

    @Id
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    private ChristianitySubcategory subcategory;
}