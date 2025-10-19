package com.articles.crm.modules.ai.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReflectionResult {
    private String contentHtml;
    private Metadata metadata;

    public String getContentHtml() { return contentHtml; }
    public void setContentHtml(String contentHtml) { this.contentHtml = contentHtml; }

    public Metadata getMetadata() { return metadata; }
    public void setMetadata(Metadata metadata) { this.metadata = metadata; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metadata {
        private String title;
        private String description;
        private String canonical;
        private String[] keywords;
        private String ogTitle;
        private String ogDescription;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getCanonical() { return canonical; }
        public void setCanonical(String canonical) { this.canonical = canonical; }
        public String[] getKeywords() { return keywords; }
        public void setKeywords(String[] keywords) { this.keywords = keywords; }
        public String getOgTitle() { return ogTitle; }
        public void setOgTitle(String ogTitle) { this.ogTitle = ogTitle; }
        public String getOgDescription() { return ogDescription; }
        public void setOgDescription(String ogDescription) { this.ogDescription = ogDescription; }
    }
}