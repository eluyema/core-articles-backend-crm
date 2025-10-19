package com.articles.crm.modules.ai.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenAiImageRequest {
    @Builder.Default
    private String model = "gpt-image-1";

    @NotBlank
    private String prompt;

    private String background;
    private String moderation;
    private Integer output_compression;
    private String output_format;
    private String quality;
    private String size;

    @Min(1) @Max(10)
    private Integer n;

    private Integer partial_images;
    private Boolean stream;
    private String response_format;
    private String style;
    private String user;
}