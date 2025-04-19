package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.entity.Category;
import com.example.mobileapi.entity.Product;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.mapper.ProductMapper;
import com.example.mobileapi.repository.ProductRepository;
import com.example.mobileapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryServiceImpl categoryService;

    ProductMapper productMapper;

    @Override
    public ProductResponseDTO saveProduct(ProductRequestDTO dto) {
        return productMapper.toProductResponseDTO(productRepository.save(productMapper.toProduct(dto)));

    }

    @Override
    public ProductResponseDTO updateProduct(Integer id, ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toProduct(productRequestDTO);
        product.setId(id);
        productRepository.save(product);
        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productMapper.toProductResponseDTOList(productRepository.findAll());
    }

    @Override
    public ProductResponseDTO getProductById(Integer id) throws AppException {
        return productMapper.toProductResponseDTO(productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)));
    }

    @Override
    public List<ProductResponseDTO> findByCategoryId(Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return productMapper.toProductResponseDTOList(products);

    }

    @Override
    public List<ProductResponseDTO> getProductByName(String nameProduct) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(nameProduct);
        return productMapper.toProductResponseDTOList(products);
    }

    @Override
    public List<ProductResponseDTO> findByNameContainingIgnoreCase(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toProductResponseDTOList(products);
    }

    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }
}
