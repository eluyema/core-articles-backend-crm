package com.articles.crm.modules.verses.useCases;

import com.articles.crm.modules.verses.dtos.VersePreview;
import com.articles.crm.modules.verses.entities.VersePost;
import com.articles.crm.modules.verses.services.VersePostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetVersePreviewListUseCase {
    private final VersePostRepository versePostRepository;

    public GetVersePreviewListUseCase(VersePostRepository versePostRepository) {
        this.versePostRepository = versePostRepository;
    }

    public List<VersePreview> handle() {
        List<VersePost> verses = versePostRepository.findAll();

        return verses.stream().flatMap(
                (verse) ->
                        verse.getVerse().getTranslations().stream()
                            .map(verseTranslate ->
                                new VersePreview(
                                    verse.getSlug(),
                                    verseTranslate.getLanguage(),
                                    verseTranslate.getContent(),
                                    verse.getVerse().getBook().getId(),
                                    verse.getVerse().getChapter(),
                                    verse.getVerse().getVerse(),
                                    verse.getCreatedAt().toString(),
                                    verse.getUpdatedAt().toString()
                                )
                            )
                ).toList();
    }
}
