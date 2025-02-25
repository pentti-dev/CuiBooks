package com.example.mobileapi.service;

import com.example.mobileapi.dto.response.UrlResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    UrlResponse uploadImage(MultipartFile file);
}
