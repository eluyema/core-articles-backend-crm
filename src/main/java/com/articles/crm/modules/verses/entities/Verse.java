package com.articles.crm.modules.verses.entities;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "VERSES")
@Access(AccessType.FIELD)
public class Verse {
    @EmbeddedId
    private VerseId id;

    @MapsId("bookId")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;


    @OneToMany(mappedBy = "verse", orphanRemoval = true)
    private Set<VerseTranslation> translations = new LinkedHashSet<>();


    @OneToOne(mappedBy = "verse", orphanRemoval = true, fetch = FetchType.LAZY)
    private NoticedVerse noticed;


    protected Verse() {}


    public Verse(Book book, int chapter, int verse) {
        this.book = book;
        this.id = new VerseId(book.getId(), chapter, verse);
    }


    public VerseId getId() { return id; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public int getChapter() { return id.getChapter(); }
    public int getVerse() { return id.getVerse(); }
    public Set<VerseTranslation> getTranslations() { return translations; }
    public NoticedVerse getNoticed() { return noticed; }
}