package vn.edu.hcmuaf.fit.fahabook.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.dto.response.UrlResponse;
import vn.edu.hcmuaf.fit.fahabook.service.ImageUploadService;

@RestController
@RequestMapping("/api/cloudinary")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Tag(name = "ImageUpload", description = "Image Upload API")
public class ImageUploadController {
    private ImageUploadService imageUploadService;

    @Operation(summary = "Upload image to Cloudinary")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<UrlResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        return imageUploadService.uploadImage(file);
    }
}
