package com.articles.crm.modules.verses.services;

import org.springframework.stereotype.Component;

@Component
public class VerseReflectionPromptBuilder {

    public String systemPromptForJson(String language) {
        return """
               You are an insightful Christian content writer who creates spiritually deep, SEO-optimized reflections.
               Output must be STRICT JSON (no markdown, no code fences, no commentary).
               Write naturally and emotionally in the target language: %s.
               """.formatted(language);
    }

    public String userPromptForJson(String verseText,
                                    String reference,
                                    String translationName,
                                    String language) {
        return """
               Create a long, inspiring reflection article **and** full SEO metadata for a blog post about this Bible verse.

               Verse text: "%s"
               Reference: %s
               Translation: %s
               Language: %s

               Return STRICT JSON with this exact structure:
               {
                 "contentHtml": "<article>...</article>",    // Valid HTML only. Must include: <article> root, one <h1>, two <h2> sections, 6–10 <p> paragraphs, at least one <ul> or <ol> list (key lessons or takeaways), one <blockquote> with a meaningful phrase from the reflection, one <strong> emphasized statement, and end with a clear practical takeaway or short prayer.
                 "metadata": {
                   "title": "string",            // readable SEO-friendly title (can mirror H1)
                   "description": "string",      // 150–160-char meta description
                   "canonical": "string",        // canonical path or absolute URL
                   "keywords": ["string", ...],  // 8–12 relevant search terms
                   "ogTitle": "string",          // Open Graph title
                   "ogDescription": "string"     // ≤200-char Open Graph description
                 }
               }

               Constraints:
               - 700–1000 words total inside contentHtml for SEO depth.
               - Use the reference once in <h1> or first <p>.
               - Use descriptive subheadings (<h2>) to divide sections (e.g., "Context and Meaning", "Modern Application").
               - Include at least one <ul> or <ol> list with practical lessons or reflection points.
               - Include one <blockquote> with a memorable or poetic insight.
               - Include <strong> to emphasize a central truth or call to action.
               - Keep tone warm, faithful, and devotional but well-researched.
               - Avoid repetition; keep paragraphs short (2–4 sentences).
               - No external links, scripts, or inline CSS.
               - Output ONLY the JSON object; no extra text or commentary.
               """.formatted(escape(verseText), reference, translationName, language);
    }

    private static String escape(String s) {
        return s == null ? "" : s.replace("\"", "\\\""); // escape for JSON
    }
}
