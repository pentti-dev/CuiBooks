package com.example.mobileapi.service.impl;

import com.cloudinary.Cloudinary;
import com.example.mobileapi.dto.response.UrlResponse;
import com.example.mobileapi.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public UrlResponse uploadImage(MultipartFile file) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("folder", "Dynamic folders"); // Optional: Set a folder name in Cloudinary
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), params);
            String cdnUrl = (String) result.get("secure_url");

            return UrlResponse.builder()
                    .url(cdnUrl)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
