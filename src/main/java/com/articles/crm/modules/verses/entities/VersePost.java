package com.articles.crm.modules.verses.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "VERSE_POST",
        uniqueConstraints = @UniqueConstraint(columnNames = {"BOOK_ID","CHAPTER","VERSE"})
)
@Access(AccessType.FIELD)
public class VersePost {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "VERSE_POST_ID", nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "SLUG", nullable = false, length = 400, unique = true)
    private String slug;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "BOOK_ID",   referencedColumnName = "BOOK_ID",   nullable = false),
            @JoinColumn(name = "CHAPTER",   referencedColumnName = "CHAPTER",   nullable = false),
            @JoinColumn(name = "VERSE",     referencedColumnName = "VERSE",     nullable = false)
    })
    private Verse verse;

    @Column(name = "CREATED_AT", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Set<VersePostTranslation> translations = new LinkedHashSet<>();

    protected VersePost() {}

    public VersePost(String slug, Verse verse) {
        this.slug = slug;
        this.verse = verse;
    }

    public static String slugFromVerse(VerseTranslation verseTranslation) {
        Verse verse = verseTranslation.getVerse();

        Book book = verse.getBook();

        String verseText = verseTranslation.getContent();

        String input = book.getName() + " " + verse.getChapter() + " " + verse.getVerse() + " " + verseText;

        String noAccents = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        String lower = noAccents.toLowerCase(Locale.ROOT);

        String hyphenated = lower.replaceAll("[^a-z0-9]+", "-");

        String trimmed = hyphenated.replaceAll("^-+|-+$", "");

        return trimmed;
    }
}