package com.articles.crm.modules.verses.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "VERSE_POST_TRANSLATIONS")
@Access(AccessType.FIELD)
public class VersePostTranslation {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "VERSE_POST_TRANSLATION_ID", nullable = false, columnDefinition = "UUID")
    private UUID id;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "VERSE_POST_ID", nullable = false)
    private VersePost post;


    @Column(name = "LANGUAGE", nullable = false, length = 5)
    private String language;


    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "metadata", nullable = false, columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "image_url", nullable = true, columnDefinition = "TEXT")
    private String imageUrl;


    protected VersePostTranslation() {}


    public VersePostTranslation(VersePost post, String language, String content, String metadata, String imageUrl) {
        this.post = post;
        this.language = language;
        this.content = content;
        this.metadata = metadata;
        this.imageUrl = imageUrl;
    }


    public UUID getId() { return id; }
    public VersePost getPost() { return post; }
    public String getLanguage() { return language; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public String getMetadata() { return metadata; }
}
