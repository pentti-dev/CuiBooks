package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.entity.enums.BookForm;
import com.example.mobileapi.entity.enums.Language;
import com.example.mobileapi.exception.AppException;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO updateProduct(UUID id, ProductRequestDTO productRequestDTO);

    void deleteProduct(UUID id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(UUID id) throws AppException;

    List<ProductResponseDTO> findByCategoryId(UUID categoryId);

    List<ProductResponseDTO> getProductByName(String nameProduct);

    List<ProductResponseDTO> findByNameContainingIgnoreCase(String name);

    List<ProductResponseDTO> filterProducts(String name, UUID categoryId,
                                            Language language, Integer minPrice, Integer maxPrice, BookForm form);
}
