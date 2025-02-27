package com.example.mobileapi.controller;

import com.example.mobileapi.dto.response.UrlResponse;
import com.example.mobileapi.service.ImageUploadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cloudinary")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Tag(name = "ImageUpload", description = "Image Upload API")
public class ImageUploadController {
    @Autowired
    ImageUploadService imageUploadService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UrlResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageUploadService.uploadImage(file));
    }


}
