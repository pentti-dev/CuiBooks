package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    Integer saveProduct(ProductRequestDTO productRequestDTO);
    void updateProduct(int id, ProductRequestDTO productRequestDTO);
    void deleteProduct(int id);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Integer id);
    List<ProductResponseDTO> findByCategoryId(Integer categoryId);

    List<ProductResponseDTO> getProductByName(String nameProduct);

    List<ProductResponseDTO> findByNameContainingIgnoreCase(String name);
}
