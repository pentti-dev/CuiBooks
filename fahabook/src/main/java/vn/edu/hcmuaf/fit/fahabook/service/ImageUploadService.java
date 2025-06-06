package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import vn.edu.hcmuaf.fit.fahabook.dto.response.UrlResponse;

public interface ImageUploadService {
    CompletableFuture<UrlResponse> uploadImage(MultipartFile file);
}
