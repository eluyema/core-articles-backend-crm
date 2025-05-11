package com.articles.crm.modules.image.useCases;

import com.articles.crm.modules.image.dto.CreatedImage;
import com.articles.crm.modules.image.entities.ArticleImage;
import com.articles.crm.modules.image.services.ArticleImageRepository;
import com.articles.crm.modules.image.services.BlurImageService;
import com.articles.crm.modules.image.services.ImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadArticleImageUseCase {
    private final ImageUploadService imageUploadService;
    private final ArticleImageRepository repository;
    private final BlurImageService blurImageService;

    public UploadArticleImageUseCase(ImageUploadService imageUploadService, ArticleImageRepository repository,
                                     BlurImageService blurImageService) {
        this.imageUploadService = imageUploadService;
        this.blurImageService = blurImageService;
        this.repository = repository;
    }

    public CreatedImage handle(MultipartFile file, boolean blurred) throws IOException {
        String url = imageUploadService.uploadImage(file);
        String blurredImage = null;
        if (repository.existsByImageUrl(url)) {
            throw new IllegalArgumentException("Image with this URL already exists");
        }

        if (blurred) {
            blurredImage = blurImageService.generateBase64BlurredImage(file);
        }

        long size = file.getSize();

        ArticleImage image = new ArticleImage(url, size);
        repository.save(image);

        return new CreatedImage(url, blurredImage);
    }
}
