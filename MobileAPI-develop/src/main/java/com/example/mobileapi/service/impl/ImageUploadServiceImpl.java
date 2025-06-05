package com.example.mobileapi.service.impl;

import com.cloudinary.Cloudinary;
import com.example.mobileapi.dto.response.UrlResponse;
import com.example.mobileapi.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ImageUploadServiceImpl implements ImageUploadService {

    private static final Logger log = LoggerFactory.getLogger(ImageUploadServiceImpl.class);
    Cloudinary cloudinary;

    public CompletableFuture<UrlResponse> uploadImage(MultipartFile file) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("folder", "Dynamic folders"); // Optional: Set a folder name in Cloudinary
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), params);
            String cdnUrl = (String) result.get("secure_url");

            // Trả về CompletableFuture chứa UrlResponse
            return CompletableFuture.completedFuture(
                    UrlResponse.builder()
                            .url(cdnUrl)
                            .build());
        } catch (IOException e) {
            log.error("Error uploading image: {}", e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

}
