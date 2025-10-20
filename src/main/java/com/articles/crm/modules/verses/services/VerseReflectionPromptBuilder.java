package com.articles.crm.modules.verses.services;

import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class VerseReflectionPromptBuilder {

    public String systemPromptForJson(String language) {
        return """
               You are an insightful Christian content writer who produces spiritually deep, SEO-optimized reflections.
               Respond only with STRICT VALID JSON (parsable by JSON parsers).
               Write naturally in the target language: %s.
               """.formatted(language);
    }

    public String userPromptForJson(String verseText,
                                    String reference,
                                    String translationName,
                                    String language) {

        int variant = ThreadLocalRandom.current().nextInt(1, 61);

        return switch (variant) {
            case 1  -> structureOne(verseText, reference, translationName, language);
            case 2  -> structureTwo(verseText, reference, translationName, language);
            case 3  -> structureThree(verseText, reference, translationName, language);
            case 4  -> structureFour(verseText, reference, translationName, language);
            case 5  -> structureFive(verseText, reference, translationName, language);
            case 6  -> structureSix(verseText, reference, translationName, language);
            case 7  -> structureSeven(verseText, reference, translationName, language);
            case 8  -> structureEight(verseText, reference, translationName, language);
            case 9  -> structureNine(verseText, reference, translationName, language);
            case 10 -> structureTen(verseText, reference, translationName, language);
            case 11 -> structure11(verseText, reference, translationName, language);
            case 12 -> structure12(verseText, reference, translationName, language);
            case 13 -> structure13(verseText, reference, translationName, language);
            case 14 -> structure14(verseText, reference, translationName, language);
            case 15 -> structure15(verseText, reference, translationName, language);
            case 16 -> structure16(verseText, reference, translationName, language);
            case 17 -> structure17(verseText, reference, translationName, language);
            case 18 -> structure18(verseText, reference, translationName, language);
            case 19 -> structure19(verseText, reference, translationName, language);
            case 20 -> structure20(verseText, reference, translationName, language);
            case 21 -> structure21(verseText, reference, translationName, language);
            case 22 -> structure22(verseText, reference, translationName, language);
            case 23 -> structure23(verseText, reference, translationName, language);
            case 24 -> structure24(verseText, reference, translationName, language);
            case 25 -> structure25(verseText, reference, translationName, language);
            case 26 -> structure26(verseText, reference, translationName, language);
            case 27 -> structure27(verseText, reference, translationName, language);
            case 28 -> structure28(verseText, reference, translationName, language);
            case 29 -> structure29(verseText, reference, translationName, language);
            case 30 -> structure30(verseText, reference, translationName, language);
            case 31 -> structure31(verseText, reference, translationName, language);
            case 32 -> structure32(verseText, reference, translationName, language);
            case 33 -> structure33(verseText, reference, translationName, language);
            case 34 -> structure34(verseText, reference, translationName, language);
            case 35 -> structure35(verseText, reference, translationName, language);
            case 36 -> structure36(verseText, reference, translationName, language);
            case 37 -> structure37(verseText, reference, translationName, language);
            case 38 -> structure38(verseText, reference, translationName, language);
            case 39 -> structure39(verseText, reference, translationName, language);
            case 40 -> structure40(verseText, reference, translationName, language);
            case 41 -> structure41(verseText, reference, translationName, language);
            case 42 -> structure42(verseText, reference, translationName, language);
            case 43 -> structure43(verseText, reference, translationName, language);
            case 44 -> structure44(verseText, reference, translationName, language);
            case 45 -> structure45(verseText, reference, translationName, language);
            case 46 -> structure46(verseText, reference, translationName, language);
            case 47 -> structure47(verseText, reference, translationName, language);
            case 48 -> structure48(verseText, reference, translationName, language);
            case 49 -> structure49(verseText, reference, translationName, language);
            case 50 -> structure50(verseText, reference, translationName, language);
            case 51 -> structure51(verseText, reference, translationName, language);
            case 52 -> structure52(verseText, reference, translationName, language);
            case 53 -> structure53(verseText, reference, translationName, language);
            case 54 -> structure54(verseText, reference, translationName, language);
            case 55 -> structure55(verseText, reference, translationName, language);
            case 56 -> structure56(verseText, reference, translationName, language);
            case 57 -> structure57(verseText, reference, translationName, language);
            case 58 -> structure58(verseText, reference, translationName, language);
            case 59 -> structure59(verseText, reference, translationName, language);
            default -> structure60(verseText, reference, translationName, language);
        };
    }

    // ===== Original 10 variants =====

    private String structureOne(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with one <h1>, two <h2> (“Context and Meaning”, “Personal Application”), 8–10 <p>, one <blockquote>, one <ul> with 5 lessons, one <strong> key insight.
            - Style: Deep, reflective, slightly poetic; end with a short prayer.
        """);
    }

    private String structureTwo(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, <h2> (“Historical Background”), <h2> (“Faith in Practice”), 6–8 <p>, one <ol> list, one <blockquote> of verse meaning, end with a “Takeaway”.
            - Style: Expository, pastoral, emphasizing spiritual discipline.
        """);
    }

    private String structureThree(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, <h2> (“The Message”), <h2> (“Living It Out”), 6 <p>, one <ul> of key insights, one <strong>, one <blockquote>.
            - Style: Practical devotional, relevant to daily life.
        """);
    }

    private String structureFour(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, 3 <h2> (“Meaning”, “Challenge”, “Encouragement”), 8–9 <p>, one <ol> (action steps), one <blockquote>, one <strong>.
            - Style: Motivational and action-oriented.
        """);
    }

    private String structureFive(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, <h2> (“Context”), <h2> (“Reflection”), 7–8 <p>, one <ul> of bullet insights, one <blockquote>, <strong> main lesson.
            - Style: Contemplative, for quiet time readers.
        """);
    }

    private String structureSix(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, <h2> (“Understanding the Passage”), <h2> (“Applying the Truth”), 8 <p>, one <ol> of application steps, <blockquote>, <strong> closing insight.
            - Style: Explanatory and pastoral.
        """);
    }

    private String structureSeven(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, <h2> (“The Verse in Context”), <h2> (“Lessons for Today”), <h2> (“Prayer”), 9 <p>, <ul> of 3–5 points, <blockquote>, and <strong>.
            - Style: Devotional and prayer-focused.
        """);
    }

    private String structureEight(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, 2 <h2> sections, one <ol> (practical life principles), 6–8 <p>, <blockquote>, <strong> final encouragement.
            - Style: Inspirational, good for newsletters/blogs.
        """);
    }

    private String structureNine(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, 3 <h2> (“Message”, “Faith in Action”, “Hope in Christ”), 10 <p>, one <ul> of faith principles, <blockquote>, <strong>.
            - Style: Evangelical and faith-affirming.
        """);
    }

    private String structureTen(String v, String r, String t, String lang) {
        return basePrompt(v, r, t, lang, """
            - Structure: <article> with <h1>, <h2> (“Scriptural Insight”), <h2> (“Modern Reflection”), 8–9 <p>, <ul> of 4 insights, <blockquote>, <strong>, ending with 1-sentence prayer.
            - Style: Balanced teaching and reflection.
        """);
    }

    // ===== 50 NEW variants (11..60) =====

    private String structure11(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> with <h1>, <h2>(“Key Terms”), <h2>(“Why It Matters Today”), 7–9 <p>, <ul> defining 4–5 terms, <blockquote>, <strong> takeaway.
        - Style: Word-study meets application.
    """); }

    private String structure12(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> with <h1>, <h2>(“Narrative Setting”), <h2>(“Discipleship Lessons”), 8 <p>, <ol> with 5 steps, <blockquote>, <strong>.
        - Style: Narrative exposition.
    """); }

    private String structure13(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Theological Insight”), <h2>(“Heart Response”), 6–8 <p>, <ul> of doctrinal points, <blockquote>, <strong>.
        - Style: Gentle theological, warm tone.
    """); }

    private String structure14(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Context”), <h2>(“Practice”), <h2>(“Prayer”), 9–10 <p>, <ol> 3–5 tasks, <blockquote>, <strong>.
        - Style: Devotional handbook.
    """); }

    private String structure15(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Problem → Gospel), 7–8 <p>, <ul> “common mistakes”, <blockquote>, <strong> gospel-centered line.
        - Style: Pastoral counseling tone.
    """); }

    private String structure16(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Old Testament Echoes”), <h2>(“New Testament Fulfillment”), 8–9 <p>, <ul> echoes, <blockquote>, <strong>.
        - Style: Biblical-theology bridge.
    """); }

    private String structure17(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“What It Says”), <h2>(“What We Do”), 6–8 <p>, <ol> of 4 actions, <blockquote>, <strong>.
        - Style: Simple clarity, action-forward.
    """); }

    private String structure18(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, three <h2> (Observation, Interpretation, Application), 9 <p>, <ul> key observations, <blockquote>, <strong>.
        - Style: Inductive study method.
    """); }

    private String structure19(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Original Audience”), <h2>(“Timeless Truth”), 8 <p>, <ul> of timeless truths, <blockquote>, <strong>.
        - Style: Historically aware devotion.
    """); }

    private String structure20(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Common Misreadings”), <h2>(“Faithful Reading”), 7–9 <p>, <ul> misconceptions, <blockquote>, <strong>.
        - Style: Corrective but gentle.
    """); }

    private String structure21(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Promise / Response), 7–8 <p>, <ol> response steps, <blockquote>, <strong>.
        - Style: Promise-centered encouragement.
    """); }

    private String structure22(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Grace / Obedience), 8–9 <p>, <ul> grace markers, <blockquote>, <strong>.
        - Style: Grace-forward discipleship.
    """); }

    private String structure23(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Head / Heart), 7–8 <p>, <ol> head-to-heart practices, <blockquote>, <strong>.
        - Style: Head–heart integration.
    """); }

    private String structure24(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, three <h2> (God, Self, Others), 9–10 <p>, <ul> relational implications, <blockquote>, <strong>.
        - Style: Community shaping.
    """); }

    private String structure25(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Truth / Practice), 6–8 <p>, <ul> mistakes to avoid, <blockquote>, <strong>.
        - Style: Practical holiness.
    """); }

    private String structure26(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Key Image/Metaphor”), <h2>(“Living the Image”), 7–9 <p>, <ul> metaphor cues, <blockquote>, <strong>.
        - Style: Imagery-driven.
    """); }

    private String structure27(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, 2 <h2> (Comfort / Call), 8–9 <p>, <ol> of calling steps, <blockquote>, <strong>.
        - Style: Comfort then challenge.
    """); }

    private String structure28(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, (Questions & Answers) as two <h2>, 7–8 <p>, <ul> FAQs, <blockquote>, <strong>.
        - Style: Q&A catechetical.
    """); }

    private String structure29(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Gospel Angle”), <h2>(“Everyday Discipleship”), 8 <p>, <ul> habits, <blockquote>, <strong>.
        - Style: Gospel-centered habits.
    """); }

    private String structure30(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, 3 <h2> (Observe, Reflect, Act), 9 <p>, <ol> 4 action items, <blockquote>, <strong>.
        - Style: Clear, actionable.
    """); }

    private String structure31(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Tensions in the Text”), <h2>(“How Grace Resolves”), 8–9 <p>, <ul> tensions, <blockquote>, <strong>.
        - Style: Honest & hopeful.
    """); }

    private String structure32(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“What It Reveals About God”), <h2>(“Our Response”), 7–8 <p>, <ol> 3–5 responses, <blockquote>, <strong>.
        - Style: God-centered.
    """); }

    private String structure33(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Joy / Suffering), 8 <p>, <ul> paradox points, <blockquote>, <strong>.
        - Style: Suffering & hope.
    """); }

    private String structure34(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Identity / Mission), 8–9 <p>, <ul> identity statements, <blockquote>, <strong>.
        - Style: Identity to mission.
    """); }

    private String structure35(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Common Objections”), <h2>(“Gospel Answers”), 7–8 <p>, <ol> objections + answers, <blockquote>, <strong>.
        - Style: Apologetics lite.
    """); }

    private String structure36(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Spiritual Practices”), <h2>(“Sustaining Grace”), 8–9 <p>, <ul> practices, <blockquote>, <strong>.
        - Style: Formation-focused.
    """); }

    private String structure37(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, three <h2> (Past, Present, Future), 9–10 <p>, <ul> hope markers, <blockquote>, <strong>.
        - Style: Redemptive timeline.
    """); }

    private String structure38(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Sin / Grace), 7–9 <p>, <ol> repentance steps, <blockquote>, <strong>.
        - Style: Repentance & renewal.
    """); }

    private String structure39(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Prayerful Reading”), <h2>(“Living the Prayer”), 8 <p>, <ul> prayer prompts, <blockquote>, <strong>.
        - Style: Prayer-led reflection.
    """); }

    private String structure40(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, three <h2> (See, Believe, Walk), 9 <p>, <ol> discipleship steps, <blockquote>, <strong>.
        - Style: Simple discipleship path.
    """); }

    private String structure41(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Holiness / Compassion), 8–9 <p>, <ul> balance points, <blockquote>, <strong>.
        - Style: Balanced virtue.
    """); }

    private String structure42(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“From Fear to Trust”), <h2>(“Practicing Trust”), 8 <p>, <ol> trust practices, <blockquote>, <strong>.
        - Style: Anxiety → trust.
    """); }

    private String structure43(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Wisdom / Folly), 7–9 <p>, <ul> wise choices, <blockquote>, <strong>.
        - Style: Proverbs-like clarity.
    """); }

    private String structure44(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Jesus-Centered Reading”), <h2>(“Following Jesus Today”), 8–9 <p>, <ul> Christ-focused points, <blockquote>, <strong>.
        - Style: Christocentric.
    """); }

    private String structure45(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, three <h2> (Hear, Receive, Respond), 8–9 <p>, <ol> 4 responses, <blockquote>, <strong>.
        - Style: Hearing God’s Word.
    """); }

    private String structure46(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Faith / Works), 8–9 <p>, <ul> harmony points, <blockquote>, <strong>.
        - Style: Faith-working-through-love.
    """); }

    private String structure47(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Gifts of God”), <h2>(“Stewardship Today”), 8 <p>, <ol> stewardship habits, <blockquote>, <strong>.
        - Style: Stewardship & gratitude.
    """); }

    private String structure48(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Suffering Transformed”), <h2>(“Comfort to Others”), 8–9 <p>, <ul> comfort practices, <blockquote>, <strong>.
        - Style: Comforted to comfort.
    """); }

    private String structure49(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, three <h2> (Know, Love, Serve), 9–10 <p>, <ul> service ideas, <blockquote>, <strong>.
        - Style: Love-in-action.
    """); }

    private String structure50(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Kingdom Vision”), <h2>(“Kingdom Practice”), 8 <p>, <ol> kingdom habits, <blockquote>, <strong>.
        - Style: Kingdom-focused.
    """); }

    private String structure51(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Humility / Courage), 8–9 <p>, <ul> humility habits, <blockquote>, <strong>.
        - Style: Virtue formation.
    """); }

    private String structure52(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Prayer / Action), 7–8 <p>, <ol> pray-then-act steps, <blockquote>, <strong>.
        - Style: Prayerful activism.
    """); }

    private String structure53(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Identity in Christ”), <h2>(“Walking Worthy”), 8–9 <p>, <ul> identity prompts, <blockquote>, <strong>.
        - Style: Identity → walk.
    """); }

    private String structure54(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Hearing the Call”), <h2>(“Obedience Today”), 8 <p>, <ol> obedience steps, <blockquote>, <strong>.
        - Style: Call & response.
    """); }

    private String structure55(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, 3 <h2> (Receive, Rest, Run), 9–10 <p>, <ul> rhythms, <blockquote>, <strong>.
        - Style: Rhythms of grace.
    """); }

    private String structure56(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Light / Darkness), 8–9 <p>, <ul> contrasts, <blockquote>, <strong>.
        - Style: Contrast teaching.
    """); }

    private String structure57(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Hope / Perseverance), 8 <p>, <ol> perseverance practices, <blockquote>, <strong>.
        - Style: Persevering hope.
    """); }

    private String structure58(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, <h2>(“Community Life”), <h2>(“Serving One Another”), 8–9 <p>, <ul> one-another’s, <blockquote>, <strong>.
        - Style: Church life.
    """); }

    private String structure59(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, 3 <h2> (Receive the Word, Guard the Word, Share the Word), 9–10 <p>, <ul> practices, <blockquote>, <strong>.
        - Style: Word-centered.
    """); }

    private String structure60(String v, String r, String t, String lang) { return basePrompt(v, r, t, lang, """
        - Structure: <article> <h1>, two <h2> (Peace / Mission), 8–9 <p>, <ul> peace practices, <blockquote>, <strong>, optional <hr> before closing prayer.
        - Style: Peace that sends.
    """); }


    private String basePrompt(String verseText, String reference, String translationName, String language, String structureRules) {
        return """
               Create a rich reflection article **and** full SEO metadata for a blog post about this Bible verse.

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

               Content & JSON rules:
               - Use the reference once in <h1> or first <p>.
               %s
               - Aim for 300–900 words total, depending on the depth and richness of the verse’s message.
               - Keep paragraphs 2–4 sentences; avoid repetition; warm devotional tone; historically aware where helpful.
               - Include exactly one <blockquote> and at least one list (<ul> or <ol>).
               - Include exactly one <strong> emphasized statement.
               - No external links, scripts, or inline CSS.
               - Escape internal quotes (\\") and backslashes (\\\\) and newlines (\\n) inside JSON strings.
               - Output ONLY the JSON object, no markdown or commentary outside the braces.
               """.formatted(escape(verseText), reference, translationName, language, structureRules);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\n");
    }
}
