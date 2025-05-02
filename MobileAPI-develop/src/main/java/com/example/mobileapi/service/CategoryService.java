package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CategoryRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.dto.response.CategoryResponseDTO;
import com.example.mobileapi.entity.Category;
import com.example.mobileapi.exception.AppException;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO saveCategory(CategoryRequestDTO category);

    CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO category) throws AppException;

    void deleteCategory(UUID  id);

    CategoryResponseDTO getCategory(UUID  id) throws AppException;

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(UUID  categoryId) throws AppException;

    Category getCategoryByName(String name) throws AppException;
}
