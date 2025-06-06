package vn.edu.hcmuaf.fit.fahabook.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CategoryRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CategoryResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequestDTO dto);

    Category toCategory(CategoryResponseDTO dto);

    CategoryResponseDTO toCategoryResponseDTO(Category category);

    CategoryRequestDTO toCategoryRequestDTO(Category entiry);

    List<CategoryResponseDTO> toCategoryResponseDTOs(List<Category> categories);

    List<Category> toCategories(List<CategoryRequestDTO> categoryRequestDTOS);

    List<Category> toCategorieList(List<CategoryResponseDTO> dtos);

    List<CategoryResponseDTO> toCategoryResponseDTOsFromEntities(List<Category> categories);

    void updateCategoryFromDto(CategoryRequestDTO dto, @MappingTarget Category entity);
}
