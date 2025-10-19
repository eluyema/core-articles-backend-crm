package com.articles.crm.modules.verses.useCases;

import com.articles.crm.modules.verses.dtos.VersePostTranslationInfo;
import com.articles.crm.modules.verses.entities.Verse;
import com.articles.crm.modules.verses.entities.VersePostTranslation;
import com.articles.crm.modules.verses.entities.VerseTranslation;
import com.articles.crm.modules.verses.services.VersePostTranslationsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class GetVersePostBySlugAndLangUseCase {
    private VersePostTranslationsRepository versePostTranslationsRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public GetVersePostBySlugAndLangUseCase(VersePostTranslationsRepository versePostTranslationsRepository) {
        this.versePostTranslationsRepository = versePostTranslationsRepository;
    }

    public VersePostTranslationInfo handle(String slug, String lang) throws Exception {
        VersePostTranslation versePostTranslation = versePostTranslationsRepository.findByPost_SlugAndLanguage(slug, lang)
                .orElseThrow(() -> new Exception("Not found verse post"));

        Verse verse = versePostTranslation.getPost().getVerse();

        VerseTranslation verseTranslation = verse.getTranslations().stream().filter((vt) -> vt.getLanguage().equals(lang))
                .findFirst().orElseThrow(() -> new Exception("Not found verse translation"));

        return new VersePostTranslationInfo(
                versePostTranslation.getPost().getSlug(),
                versePostTranslation.getLanguage(),
                verseTranslation.getContent(),

                verse.getBook().getId(),
                verse.getChapter(),
                verse.getVerse(),

                versePostTranslation.getContent(),
                mapper.readValue(versePostTranslation.getMetadata(), Object.class),
                versePostTranslation.getPost().getCreatedAt().toString(),
                versePostTranslation.getPost().getUpdatedAt().toString()
        );
    }
}
