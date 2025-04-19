package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.CategoryRequestDTO;
import com.example.mobileapi.dto.response.CategoryResponseDTO;
import com.example.mobileapi.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequestDTO dto);

    Category toCategory(CategoryResponseDTO dto);

    CategoryResponseDTO toCategoryResponseDTO(Category category);

    CategoryRequestDTO toCategoryRequestDTO(Category entiry);

    List<CategoryResponseDTO> toCategoryResponseDTOs(List<Category> categories);

    List<Category> toCategories(List<CategoryRequestDTO> categoryRequestDTOS);

    List<Category> toCategorieList(List<CategoryResponseDTO> dtos);

    void updateCategoryFromDto(CategoryRequestDTO dto, @MappingTarget Category entity);
}
