package com.articles.crm.modules.image.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageUploadService {

    public record UploadedImage(String key, String url, long size, String contentType) {}

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.cloudfront.url}")
    private String cloudFrontUrl;

    // Optional defaults
    @Value("${cdn.cache-control:public, max-age=31536000, immutable}")
    private String defaultCacheControl;

    public ImageUploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String origName = file.getOriginalFilename();
        String ext = extFromFilenameOrDetect(origName, file.getBytes());
        String contentType = safeContentType(file.getContentType(), ext);
        UploadedImage out = uploadBytes(file.getBytes(), contentType, ext, null);
        return out.url();
    }

    /** Recommended: upload raw bytes with explicit contentType + extension. */
    public UploadedImage uploadBytes(byte[] bytes,
                                     String contentType,
                                     String extension,
                                     String keyPrefix) {
        String ext = normalizeExt(extension);
        String ct = safeContentType(contentType, ext);
        String key = buildKey(ext, keyPrefix);

        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(ct)
                .cacheControl(defaultCacheControl)
                // Keep private if using CloudFront Origin Access Control.
                // .acl(ObjectCannedACL.PUBLIC_READ) // Only if you need direct S3 public access.
                .build();

        s3Client.putObject(put, RequestBody.fromBytes(bytes));
        String url = cloudFrontUrl.endsWith("/") ? cloudFrontUrl + key : cloudFrontUrl + "/" + key;
        return new UploadedImage(key, url, bytes.length, ct);
    }

    /** Convenience: upload many images with same format & optional prefix. */
    public List<UploadedImage> uploadAll(List<byte[]> images,
                                         String contentType,
                                         String extension,
                                         String keyPrefix) {
        List<UploadedImage> result = new ArrayList<>(images.size());
        for (byte[] img : images) {
            result.add(uploadBytes(img, contentType, extension, keyPrefix));
        }
        return result;
    }

    // ---------- helpers ----------

    private static String buildKey(String ext, String prefix) {
        String base = UUID.randomUUID().toString() + "." + ext;
        if (prefix == null || prefix.isBlank()) return base;
        String p = prefix.endsWith("/") ? prefix : prefix + "/";
        return p + base;
    }

    private static String normalizeExt(String ext) {
        if (ext == null) return "png";
        String f = ext.toLowerCase();
        return switch (f) {
            case "jpeg", "jpg" -> "jpg";
            case "webp" -> "webp";
            default -> "png";
        };
    }

    private static String safeContentType(String contentType, String ext) {
        if (contentType != null && !contentType.isBlank()) return contentType;
        return switch (ext) {
            case "jpg" -> MediaType.IMAGE_JPEG_VALUE;
            case "webp" -> "image/webp";
            default -> MediaType.IMAGE_PNG_VALUE;
        };
    }

    private static String extFromFilenameOrDetect(String filename, byte[] bytes) {
        String ext = (filename != null && filename.contains("."))
                ? filename.substring(filename.lastIndexOf('.') + 1).toLowerCase()
                : null;
        if (ext != null) return normalizeExt(ext);
        // minimal magic detection
        if (bytes.length >= 8 && bytes[0]==(byte)0x89 && bytes[1]==0x50 && bytes[2]==0x4E && bytes[3]==0x47) return "png";
        if (bytes.length >= 3 && bytes[0]==(byte)0xFF && bytes[1]==(byte)0xD8) return "jpg";
        if (bytes.length >= 12 && bytes[8]==0x57 && bytes[9]==0x45 && bytes[10]==0x42 && bytes[11]==0x50) return "webp";
        return "png";
    }
}
