package com.example.mobileapi.service;

import com.example.mobileapi.dto.response.UrlResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ImageUploadService {
    CompletableFuture<UrlResponse> uploadImage(MultipartFile file);
}
