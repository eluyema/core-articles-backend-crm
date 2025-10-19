package com.articles.crm.modules.verses.entities;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "BOOKS")
@Access(AccessType.FIELD)
public class Book {
    @Id
    @Column(name = "BOOK_ID", nullable = false)
    private Integer id;


    @Column(name = "NAME", nullable = false, length = 30)
    private String name;


    @Column(name = "abbreviation", nullable = false, length = 10)
    private String abbreviation;


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Verse> verses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "verseBook", orphanRemoval = true)
    private Set<VerseTranslation> verseTranslations = new LinkedHashSet<>();


    protected Book() {}


    public Book(Integer id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }


    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAbbreviation() { return abbreviation; }
    public void setAbbreviation(String abbreviation) { this.abbreviation = abbreviation; }
    public Set<Verse> getVerses() { return verses; }
    public Set<VerseTranslation> getVerseTranslations() { return verseTranslations; }
}
