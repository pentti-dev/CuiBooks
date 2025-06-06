package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.ProductFilter;
import com.example.mobileapi.dto.request.create.ProductCreateDTO;
import com.example.mobileapi.dto.request.update.ProductUpdateDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.entity.Product;
import com.example.mobileapi.entity.enums.StockAction;
import com.example.mobileapi.exception.AppException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDTO saveProduct(ProductCreateDTO productCreateDTO);

    ProductResponseDTO updateProduct(UUID id, ProductUpdateDTO productCreateDTO);

    void deleteProduct(UUID id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(UUID id) throws AppException;

    List<ProductResponseDTO> findByCategoryId(UUID categoryId);

    List<ProductResponseDTO> getProductByName(String nameProduct);

    List<ProductResponseDTO> findByNameContainingIgnoreCase(String name);

    List<ProductResponseDTO> filterProducts(ProductFilter filterInput);

    void saveAll(List<Product> products);

    List<ProductResponseDTO> getProductSale();

    Integer getProductStock(UUID productId);

    void checkQuantityAvailability(UUID id, int inputQuantity, StockAction action);

    BigDecimal getPriceById(UUID productId);
}
