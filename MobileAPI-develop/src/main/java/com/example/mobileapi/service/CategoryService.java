package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CategoryRequestDTO;
import com.example.mobileapi.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    int saveCategory(CategoryRequestDTO category);
    void updateCategory(int id,CategoryRequestDTO category);
    void deleteCategory(int id);
    CategoryResponseDTO getCategory(int id);
    List<CategoryResponseDTO> getAllCategories();
}
