package com.articles.crm.modules.verses.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;


import java.util.UUID;

@Entity
@Table(name = "VERSE_TRANSLATIONS",
        uniqueConstraints = @UniqueConstraint(name = "UK_VERSE_TRANSLATIONS_LANG_VERSE",
                columnNames = {"LANGUAGE", "BOOK_ID", "CHAPTER", "VERSE"}))
@Access(AccessType.FIELD)
public class VerseTranslation {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "VERSE_TRANSLATION_ID", nullable = false, columnDefinition = "UUID")
    private UUID id;


    @Column(name = "LANGUAGE", nullable = false, length = 5)
    private String language;


    @Column(name = "NAME", nullable = false, length = 50)
    private String name;


    @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
    private String content;


    // Redundant FKs for the join to VERSES (denormalized for the unique constraint)
    @Column(name = "BOOK_ID", nullable = false)
    private Integer bookId;


    @Column(name = "CHAPTER", nullable = false)
    private Integer chapter;


    @Column(name = "VERSE", nullable = false)
    private Integer verseNumber;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID", insertable = false, updatable = false),
            @JoinColumn(name = "CHAPTER", referencedColumnName = "CHAPTER", insertable = false, updatable = false),
            @JoinColumn(name = "VERSE", referencedColumnName = "VERSE", insertable = false, updatable = false)
    })
    private Verse verse;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID", insertable = false, updatable = false)
    private Book verseBook;


    protected VerseTranslation() {}


    public VerseTranslation(Verse verse, String language, String name, String content) {
        this.verse = verse;
        this.language = language;
        this.name = name;
        this.content = content;
        this.bookId = verse.getId().getBookId();
        this.chapter = verse.getId().getChapter();
        this.verseNumber = verse.getId().getVerse();
        this.verseBook = verse.getBook();
    }


    public UUID getId() { return id; }
    public String getLanguage() { return language; }
    public String getName() { return name; }
    public String getContent() { return content; }
    public Verse getVerse() { return verse; }
    public Integer getBookId() { return bookId; }
    public Integer getChapter() { return chapter; }
    public Integer getVerseNumber() { return verseNumber; }
}