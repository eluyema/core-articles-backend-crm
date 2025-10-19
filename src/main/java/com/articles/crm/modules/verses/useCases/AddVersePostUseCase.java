package com.articles.crm.modules.verses.useCases;

import com.articles.crm.modules.ai.dtos.ReflectionResult;
import com.articles.crm.modules.verses.entities.*;
import com.articles.crm.modules.verses.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AddVersePostUseCase {
    private final VerseRepository verseRepository;
    private final VersePostRepository versePostRepository;
    private final NoticedVerseRepository noticedVerseRepository;
    private final VerseReflectionGenerator reflectionGenerator;
    private final ObjectMapper mapper = new ObjectMapper();

    public AddVersePostUseCase(VerseRepository verseRepository,
                               VerseReflectionGenerator reflectionGenerator, VersePostRepository versePostRepository,
                               NoticedVerseRepository noticedVerseRepository) {
        this.verseRepository = verseRepository;
        this.reflectionGenerator = reflectionGenerator;
        this.versePostRepository = versePostRepository;
        this.noticedVerseRepository = noticedVerseRepository;
    }

    @Transactional
    public void handle() throws Exception {
        Verse verseWithoutPost = verseRepository
                .findRandomWhereNoticedVersePostIsNull()
                .orElseThrow(() -> new Exception("Verses without post not found"));
        NoticedVerse noticedVerse = verseWithoutPost.getNoticed();
        Set<VerseTranslation> verseTranslations = verseWithoutPost.getTranslations();
        VerseTranslation en = verseTranslations.stream()
                .filter(vt -> "en".equals(vt.getLanguage()))
                .findFirst()
                .orElseThrow(() -> new Exception("Not found english translate of verse"));

        String slug = VersePost.slugFromVerse(en);
        VersePost versePost = new VersePost(slug, verseWithoutPost);

        Set<VersePostTranslation> versePostTranslations = new HashSet<>();

        for (VerseTranslation vt : verseTranslations) {
            String language   = vt.getLanguage();
            String reference  = verseWithoutPost.getBook().getName() + " " + vt.getChapter() + ":" + vt.getVerseNumber();


            ReflectionResult verseReflection= reflectionGenerator.generateReflection(
                    vt.getContent(),
                    reference,
                    vt.getName(),
                    language
            );

            VersePostTranslation vpt = new VersePostTranslation(
                    versePost,
                    language,
                    verseReflection.getContentHtml(),
                    mapper.writeValueAsString(verseReflection.getMetadata()),
                    null
            );

            versePostTranslations.add(vpt);
        }
        noticedVerse.setVersePost(versePost);
        versePost.getTranslations().addAll(versePostTranslations);
        versePostRepository.save(versePost);
        noticedVerseRepository.save(noticedVerse);
    }
}

