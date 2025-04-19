package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.exception.AppException;

import java.util.List;

public interface ProductService {
    ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO updateProduct(Integer id, ProductRequestDTO productRequestDTO);

    void deleteProduct(int id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Integer id) throws AppException;

    List<ProductResponseDTO> findByCategoryId(Integer categoryId);

    List<ProductResponseDTO> getProductByName(String nameProduct);

    List<ProductResponseDTO> findByNameContainingIgnoreCase(String name);
}
