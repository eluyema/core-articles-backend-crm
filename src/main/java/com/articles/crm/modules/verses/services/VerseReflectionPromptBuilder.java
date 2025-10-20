package com.articles.crm.modules.verses.services;

import org.springframework.stereotype.Component;

@Component
public class VerseReflectionPromptBuilder {

    public String systemPromptForJson(String language) {
        return """
               You are an insightful Christian content writer who produces spiritually deep, SEO-optimized reflections.
               Output must be STRICT VALID JSON — parsable by standard JSON parsers with no syntax errors.
               Use correct escaping for quotes (\\"), backslashes (\\\\), and line breaks (\\n).
               Write naturally in the target language: %s.
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

               Return STRICT VALID JSON with this exact structure:
               {
                 "contentHtml": "<article>...</article>",    
                 "metadata": {
                   "title": "string",            
                   "description": "string",      
                   "canonical": "string",        
                   "keywords": ["string", ...],  
                   "ogTitle": "string",          
                   "ogDescription": "string"     
                 }
               }

               Content rules:
               - <article> must include one <h1>, two <h2>, 6–10 <p>, one <ul> or <ol>, one <blockquote>, one <strong>.
               - 700–1000 words total for SEO depth.
               - End with a clear, practical spiritual takeaway or short prayer.
               - Use descriptive <h2> subheadings like “Context and Meaning”, “Modern Application”.
               - Keep paragraphs 2–4 sentences each; no inline CSS or scripts.

               JSON rules:
               - Escape all internal double quotes (\\"), backslashes (\\\\), and line breaks (\\n) in all string values.
               - Ensure every key and value is properly quoted.
               - Output ONLY the JSON object, no markdown, commentary, or text outside the braces.
               - Validate that your JSON can be parsed by a standard JSON parser before finishing.
               """.formatted(escape(verseText), reference, translationName, language);
    }

    private static String escape(String s) {
        if (s == null) return "";
        // Escape double quotes, backslashes, and control chars that break JSON
        return s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\n");
    }
}
