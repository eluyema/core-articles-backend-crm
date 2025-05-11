package com.articles.crm.modules.image.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

@Service
public class BlurImageService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateBase64BlurredImage(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Resize the image to a smaller size
        int targetWidth = 32;
        int targetHeight = 32;
        BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight);

        // Apply blur
        float[] matrix = {1/9f, 1/9f, 1/9f, 1/9f, 1/9f, 1/9f, 1/9f, 1/9f, 1/9f};
        Kernel kernel = new Kernel(3, 3, matrix);
        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage blurredImage = convolveOp.filter(resizedImage, null);

        // Convert to base64
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String formatName = getFileExtension(file.getOriginalFilename()).equalsIgnoreCase("png") ? "png" : "jpg";
        ImageIO.write(blurredImage, formatName, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        String blurDataUrl = "data:image/" + formatName + ";base64," + base64Image;

        Map<String, Object> response = new HashMap<>();
        response.put("blurDataUrl", blurDataUrl);
        response.put("originalWidth", originalWidth);
        response.put("originalHeight", originalHeight);

        try {
            return objectMapper.writeValueAsString(response);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            // Handle the exception appropriately, maybe log it and return just the blurDataUrl as a fallback
            e.printStackTrace();
            return blurDataUrl;
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty() || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}