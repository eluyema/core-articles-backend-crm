package com.articles.crm.modules.verses.entities;

import jakarta.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "NOTICED_VERSES")
@Access(AccessType.FIELD)
public class NoticedVerse {
    @EmbeddedId
    private VerseId id;


    @MapsId
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID"),
            @JoinColumn(name = "CHAPTER", referencedColumnName = "CHAPTER"),
            @JoinColumn(name = "VERSE", referencedColumnName = "VERSE")
    })
    private Verse verse;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verse_post_id")
    private VersePost versePost; // nullable, ON DELETE SET NULL


    protected NoticedVerse() {}


    public NoticedVerse(Verse verse) {
        this.verse = verse;
        this.id = verse.getId();
    }


    public VerseId getId() { return id; }
    public Verse getVerse() { return verse; }
    public VersePost getVersePost() { return versePost; }
    public void setVersePost(VersePost versePost) { this.versePost = versePost; }
}