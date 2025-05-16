package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ApiResponse;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductController {
    ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponseDTO> addProduct(@RequestBody @Valid ProductRequestDTO product) {
        return ApiResponse.<ProductResponseDTO>builder()
                .data(productService.saveProduct(product))
                .build();
    }

    @PutMapping("/{productId}")
    public ApiResponse<ProductResponseDTO> updateProduct(@PathVariable UUID productId,
                                                         @RequestBody @Valid ProductRequestDTO product) {
        return ApiResponse.<ProductResponseDTO>builder()
                .data(productService.updateProduct(productId, product))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> deleteCustomer(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ApiResponse.success("Xóa sản phẩm thành công");
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponseDTO> getCustomer(@PathVariable UUID productId) throws AppException {
        return ApiResponse.<ProductResponseDTO>builder()
                .data(productService.getProductById(productId))
                .build();
    }

    @GetMapping("/list")
    public ApiResponse<List<ProductResponseDTO>> getProducts() {
        return ApiResponse.<List<ProductResponseDTO>>builder()
                .data(productService.getAllProducts())
                .build();
    }

    @GetMapping("/list/{categoryId}")
    public ApiResponse<List<ProductResponseDTO>> getProductsByCategoryId(@PathVariable UUID categoryId) {
        return ApiResponse.<List<ProductResponseDTO>>builder()
                .data(productService.findByCategoryId(categoryId))
                .build();
    }

    @GetMapping("/list/findByName/{name}")
    public ApiResponse<List<ProductResponseDTO>> getProductsByName(@PathVariable String name) {
        return ApiResponse.<List<ProductResponseDTO>>builder()
                .data(productService.findByNameContainingIgnoreCase(name))
                .build();

    }

    @GetMapping("/categoty")
    public ApiResponse<List<ProductResponseDTO>> getProductByName(@RequestParam String name) {
        return ApiResponse.<List<ProductResponseDTO>>builder()
                .data(productService.getProductByName(name))
                .build();
    }
}
