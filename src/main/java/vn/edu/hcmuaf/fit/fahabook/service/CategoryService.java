package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.List;
import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CategoryRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CategoryResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Category;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface CategoryService {
    CategoryResponseDTO saveCategory(CategoryRequestDTO category);

    void saveAllCategoryEntries(List<Category> category);

    void saveAllCategoryDTOs(List<CategoryRequestDTO> dtos);

    CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO category) throws AppException;

    void deleteCategory(UUID id);

    CategoryResponseDTO getCategory(UUID id) throws AppException;

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(UUID categoryId) throws AppException;

    Category getCategoryByName(String name) throws AppException;
}
