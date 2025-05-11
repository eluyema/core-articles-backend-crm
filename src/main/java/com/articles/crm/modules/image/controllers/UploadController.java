package com.articles.crm.modules.image.controllers;

import com.articles.crm.modules.image.dto.CreatedImage;
import com.articles.crm.modules.image.services.ImageUploadService;
import com.articles.crm.modules.image.useCases.UploadArticleImageUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final UploadArticleImageUseCase uploadArticleImageUseCase;

    public UploadController(UploadArticleImageUseCase uploadArticleImageUseCase) {
        this.uploadArticleImageUseCase = uploadArticleImageUseCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/image")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile file,
                                                           @RequestParam(value = "blurred", required = false, defaultValue = "false") boolean blurred) {
        try {
            CreatedImage image = uploadArticleImageUseCase.handle(file, blurred);

            Map<String, Object> response = new HashMap<>();
            response.put("success", 1);
            response.put("file", Map.of("url", image.getUrl()));
            if(image.getBlurredImage()!=null) {
                response.put("blurredFile", Map.of("url", image.getBlurredImage()));
            }
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", 0, "error", e.getMessage()));
        }
    }
}

