package com.articles.crm.modules.ai.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenAiImageResponse {
    private long created;
    private java.util.List<ImageData> data;
    private Usage usage;

    @Data
    public static class ImageData {
        private String b64_json;
        private String url;
        private String revised_prompt;
    }

    @Data
    public static class Usage {
        private int total_tokens;
        private int input_tokens;
        private int output_tokens;
    }
}