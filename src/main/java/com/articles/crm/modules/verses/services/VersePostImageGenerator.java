package com.articles.crm.modules.verses.services;

import com.articles.crm.modules.ai.services.OpenAiImageService;
import com.articles.crm.modules.image.services.ImageUploadService;
import com.articles.crm.modules.ai.dtos.OpenAiImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VersePostImageGenerator {

    private final OpenAiImageService openAiImageService;
    private final VersePostPromptBuilder promptBuilder;
    private final ImageUploadService imageUploadService;

    @Value("${openai.images.default-model:gpt-image-1}")  private String defaultModel;
    @Value("${openai.images.default-size:1024x1024}")     private String defaultSize;
    @Value("${openai.images.default-quality:high}")       private String defaultQuality;
    @Value("${openai.images.default-output-format:webp}") private String defaultOutputFormat;
    @Value("${openai.images.default-background:auto}")    private String defaultBackground;
    @Value("${openai.images.default-n:1}")                private Integer defaultN;

    public List<String> generateAndUpload(String verseText,
                                          String reference,
                                          String translation,
                                          String sceneTheme,
                                          String mood,
                                          String style,
                                          Integer n,
                                          String keyPrefix) {

        String prompt = promptBuilder.buildPostPrompt(verseText, reference, translation, sceneTheme, mood, style);

        OpenAiImageRequest req = OpenAiImageRequest.builder()
                .model(defaultModel)
                .prompt(prompt)
                .size(defaultSize)
                .quality(defaultQuality)
                .background(defaultBackground)
                .output_format(defaultOutputFormat)
                .n(n == null ? defaultN : Math.max(1, Math.min(10, n)))
                .build();

        List<byte[]> images = openAiImageService.generateBytes(req);

        String ext = normalizeExt(defaultOutputFormat);
        String contentType = switch (ext) {
            case "jpg"  -> MediaType.IMAGE_JPEG_VALUE;
            case "webp" -> "image/webp";
            default     -> MediaType.IMAGE_PNG_VALUE;
        };

        var uploaded = imageUploadService.uploadAll(images, contentType, ext, keyPrefix);
        return uploaded.stream().map(ImageUploadService.UploadedImage::url).toList();
    }

    private static String normalizeExt(String fmt) {
        if (fmt == null) return "png";
        String f = fmt.toLowerCase();
        return switch (f) { case "jpeg", "jpg" -> "jpg"; case "webp" -> "webp"; default -> "png"; };
    }
}