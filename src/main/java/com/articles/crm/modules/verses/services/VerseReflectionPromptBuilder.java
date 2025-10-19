package com.articles.crm.modules.verses.services;

import org.springframework.stereotype.Component;

@Component
public class VerseReflectionPromptBuilder {

    public String systemPromptForJson(String language) {
        return """
               You are a concise Christian content writer. Output must be STRICT JSON (no markdown, no code fences, no commentary).
               Write in the target language: %s.
               """.formatted(language);
    }

    public String userPromptForJson(String verseText,
                                    String reference,
                                    String translationName,
                                    String language) {
        // We ask the model to produce only the fields we expect.
        return """
               Create a short reflection article **and** SEO metadata for a post page about this Bible verse.

               Verse text: "%s"
               Reference: %s
               Translation: %s
               Language: %s

               Return STRICT JSON with this exact structure:
               {
                 "contentHtml": "<article>...</article>",    // Valid HTML body only. Must include: <article> root, one <h1>, one <h2>, 2–4 <p>, one <strong> emphasized phrase, end with a single-sentence practical application.
                 "metadata": {
                   "title": "string",            // readable page title (can mirror H1)
                   "description": "string",      // 150–160 chars SEO description
                   "canonical": "string",        // canonical path or absolute URL
                   "keywords": ["string", ...],  // 5–10 concise terms
                   "ogTitle": "string",          // Open Graph title
                   "ogDescription": "string"     // Open Graph description (<= 200 chars)
                 }
               }

               Constraints:
               - 170–260 words in total inside contentHtml.
               - Use the reference once in <h1> or first <p>.
               - No external links, no images, no scripts, no inline CSS.
               - Output ONLY the JSON object; no extra text before or after.
               """.formatted(escape(verseText), reference, translationName, language);
    }

    private static String escape(String s) {
        return s == null ? "" : s.replace("\"", "\\\""); // escape for JSON
    }
}
