package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CategoryRequestDTO;
import com.example.mobileapi.dto.response.CartResponseDTO;
import com.example.mobileapi.dto.response.CategoryResponseDTO;
import com.example.mobileapi.entity.Category;
import com.example.mobileapi.exception.AppException;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO saveCategory(CategoryRequestDTO category);

    CategoryResponseDTO updateCategory(int id, CategoryRequestDTO category) throws AppException;

    void deleteCategory(int id);

    CategoryResponseDTO getCategory(int id) throws AppException;

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(int categoryId) throws AppException;

    Category getCategoryByName(String name) throws AppException;
}
