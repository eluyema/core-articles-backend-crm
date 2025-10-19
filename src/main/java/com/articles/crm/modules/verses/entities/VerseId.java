package com.articles.crm.modules.verses.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VerseId implements Serializable {
    @Column(name = "BOOK_ID", nullable = false)
    private Integer bookId;


    @Column(name = "CHAPTER", nullable = false)
    private Integer chapter;


    @Column(name = "VERSE", nullable = false)
    private Integer verse;

    public VerseId() {}


    public VerseId(Integer bookId, Integer chapter, Integer verse) {
        this.bookId = bookId;
        this.chapter = chapter;
        this.verse = verse;
    }


    public Integer getBookId() { return bookId; }
    public Integer getChapter() { return chapter; }
    public Integer getVerse() { return verse; }


    public void setBookId(Integer bookId) { this.bookId = bookId; }
    public void setChapter(Integer chapter) { this.chapter = chapter; }
    public void setVerse(Integer verse) { this.verse = verse; }


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerseId)) return false;
        VerseId that = (VerseId) o;
        return Objects.equals(bookId, that.bookId) &&
                Objects.equals(chapter, that.chapter) &&
                Objects.equals(verse, that.verse);
    }


    @Override public int hashCode() { return Objects.hash(bookId, chapter, verse); }
}
