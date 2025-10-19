package com.articles.crm.modules.verses.services;

import com.articles.crm.modules.ai.dtos.ReflectionResult;
import com.articles.crm.modules.ai.services.OpenAiTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerseReflectionGenerator {
    private final OpenAiTextService textService;
    private final VerseReflectionPromptBuilder promptBuilder;

    public ReflectionResult generateReflection(String verseText,
                                               String reference,
                                               String translationName,
                                               String language) {
        String system = promptBuilder.systemPromptForJson(language);
        String user   = promptBuilder.userPromptForJson(verseText, reference, translationName, language);
        return textService.chatReflectionJson(system, user);
    }
}
