package com.articles.crm.modules.verses.services;

import org.springframework.stereotype.Component;

@Component
public class VersePostPromptBuilder {
    public String buildPostPrompt(String verseText,
                                  String reference,
                                  String translation,
                                  String sceneTheme,
                                  String mood,
                                  String style) {
        return """
               Post image for a Christian website.
               Verse: "%s"
               Reference: "%s" | Translation: "%s"
               Scene: %s | Mood: %s | Style: %s
               Requirements: square ~1024x1024; large, crisp, correct lettering; strong contrast with subtle gradient if needed;
               clean negative space for text; tasteful natural look; no extra words, logos, or borders.
               """.formatted(verseText, reference, translation, sceneTheme, mood, style);
    }
}