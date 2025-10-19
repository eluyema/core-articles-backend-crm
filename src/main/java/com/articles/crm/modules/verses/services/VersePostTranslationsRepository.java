package com.articles.crm.modules.verses.services;

import com.articles.crm.modules.verses.entities.VersePostTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VersePostTranslationsRepository extends JpaRepository<VersePostTranslation, Long> {
    Optional<VersePostTranslation> findByPost_SlugAndLanguage(String slug, String language);
}
