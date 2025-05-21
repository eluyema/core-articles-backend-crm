package com.articles.crm.modules.christianity.useCases;

import com.articles.crm.modules.article.entities.Article;
import com.articles.crm.modules.article.entities.ArticleTranslation;
import com.articles.crm.modules.article.entities.ArticleTranslationContent;
import com.articles.crm.modules.article.services.ArticleTranslationRepository;
import com.articles.crm.modules.christianity.entities.ChristianityArticle;
import com.articles.crm.modules.christianity.services.ChristianityArticleRepository;
import com.articles.crm.modules.christianity.services.ChristianityCategoryRepository;
import com.articles.crm.modules.christianity.services.ChristianitySubcategoryRepository;
import com.articles.crm.modules.christianity.services.GeminiTranslator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.java.accessibility.util.Translator;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TranslateArticleUseCase {
    private final ChristianityArticleRepository christianityArticleRepository;
    private final ArticleTranslationRepository articleTranslationRepository;

    private final GeminiTranslator translator;
    ObjectMapper mapper = new ObjectMapper();

    private final List<String> availableLocales = new ArrayList<>(Arrays.asList("en", "fr", "es", "ru", "de", "pt"));

    public TranslateArticleUseCase(ChristianityArticleRepository christianityArticleRepository, GeminiTranslator translator,
                                   ArticleTranslationRepository articleTranslationRepository) {
        this.christianityArticleRepository = christianityArticleRepository;
        this.translator = translator;
        this.articleTranslationRepository = articleTranslationRepository;
    }


    public void handle(String slug, String originalLanguage) throws Exception {
        ChristianityArticle christianityArticle = christianityArticleRepository.findByArticle_Slug(slug).orElseThrow(() -> new Exception("ChristianityArticle hadn't found"));

        Article article = christianityArticle.getArticle();

        ArticleTranslation translation = article.getTranslations().stream().filter(tr -> tr.getLanguage().equals(originalLanguage)).findFirst().orElseThrow(() -> new Exception("Translation hadn't found"));

        List<ArticleTranslation> existingTranslations = article.getTranslations().stream().filter(tr-> !tr.getLanguage().equals(originalLanguage)).toList();

        List<String> newTranslationLanguages = availableLocales.stream().filter(tr ->
                !tr.equals(originalLanguage) &&
                existingTranslations.stream().noneMatch(exTr-> exTr.getLanguage().equals(tr))
        ).toList();

        String title = translation.getTitle();
        String description = translation.getDescription();
        String previewImageAlt = translation.getPreviewImageAlt();
        String content = translation.getContent().getContent();

        ObjectNode root = mapper.createObjectNode();
        root.put("title", title);
        root.put("description", description);
        root.put("previewImageAlt", previewImageAlt);
        root.set("content", mapper.readTree(content));

        for(String language : newTranslationLanguages) {
            JsonNode translatedRoot = translator.translateJson(root.toString(), originalLanguage, language);

            String translatedTitle = translatedRoot.get("title").asText();
            String translatedDescription = translatedRoot.get("description").asText();
            String translatedImageAlt = translatedRoot.get("previewImageAlt").asText();
            String translatedContent = translatedRoot.get("content").toString();

            ArticleTranslation newArticleTranslation = new ArticleTranslation();
            newArticleTranslation.setTitle(translatedTitle);
            newArticleTranslation.setDescription(translatedDescription);
            newArticleTranslation.setPreviewImageAlt(translatedImageAlt);
            newArticleTranslation.setLanguage(language);
            newArticleTranslation.setArticle(article);
            newArticleTranslation.setPreviewImageUrl(translation.getPreviewImageUrl());
            // newArticleTranslation.setImages(translation.getImages()); TODO: think about it
            ArticleTranslationContent newContent = new ArticleTranslationContent();
            newContent.setContent(translatedContent);

            newArticleTranslation.setContent(newContent);
            ArticleTranslation savedTranslation = articleTranslationRepository.save(newArticleTranslation);
            article.getTranslations().add(savedTranslation); // TODO: looks badly, should be fixed in future -_-
        }

        for(ArticleTranslation existingTranslation : existingTranslations) {
            JsonNode translatedRoot =translator.translateJson(root.toString(), originalLanguage, existingTranslation.getLanguage());

            String translatedTitle = translatedRoot.get("title").asText();
            String translatedDescription = translatedRoot.get("description").asText();
            String translatedImageAlt = translatedRoot.get("previewImageAlt").asText();
            String translatedContent = translatedRoot.get("content").toString();

            existingTranslation.setTitle(translatedTitle);
            existingTranslation.setDescription(translatedDescription);
            existingTranslation.setPreviewImageAlt(translatedImageAlt);
            existingTranslation.setLanguage(existingTranslation.getLanguage());
            existingTranslation.setArticle(article);
            existingTranslation.setPreviewImageUrl(translation.getPreviewImageUrl());
            existingTranslation.setImages(new ArrayList<>()); // TODO: potential bug :D
            ArticleTranslationContent newContent = existingTranslation.getContent();
            newContent.setContent(translatedContent);

            existingTranslation.setContent(newContent);
        }

        christianityArticleRepository.save(christianityArticle);
    }

}
