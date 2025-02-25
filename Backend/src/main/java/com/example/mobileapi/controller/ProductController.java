package com.example.mobileapi.controller;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public int addProduct(@RequestBody ProductRequestDTO product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable int productId, @RequestBody ProductRequestDTO product) {
        productService.updateProduct(productId, product);
    }

    @DeleteMapping("/{productId}")
    public void deleteCustomer(@PathVariable int productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/{productId}")
    public ProductResponseDTO getCustomer(@PathVariable int productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/list")
    public List<ProductResponseDTO> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/list/{categoryId}")
    public List<ProductResponseDTO> getProductsByCategoryId(@PathVariable int categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    @GetMapping("/list/findByName/{name}")
    public List<ProductResponseDTO> getProductsByName(@PathVariable String name) {
        return productService.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/search")
    public List<ProductResponseDTO> getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }
}
