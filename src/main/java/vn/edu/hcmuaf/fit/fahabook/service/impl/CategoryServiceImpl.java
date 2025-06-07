package vn.edu.hcmuaf.fit.fahabook.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CategoryRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CategoryResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Category;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.mapper.CategoryMapper;
import vn.edu.hcmuaf.fit.fahabook.repository.CategoryRepository;
import vn.edu.hcmuaf.fit.fahabook.service.CategoryService;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponseDTO saveCategory(CategoryRequestDTO dto) {
        if (categoryRepository.existsCategoryByCodeOrName(dto.getCode(), dto.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        Category category = categoryRepository.save(categoryMapper.toCategory(dto));
        return categoryMapper.toCategoryResponseDTO(category);
    }

    @Transactional
    @Override
    public void saveAllCategoryEntries(List<Category> category) {
        categoryRepository.saveAll(category);
    }

    @Override
    public void saveAllCategoryDTOs(List<CategoryRequestDTO> dtos) {
        List<Category> categories = categoryMapper.toCategories(dtos);
        categoryRepository.saveAll(categories);
    }

    @Override
    public CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO dto) throws AppException {
        if (!categoryRepository.existsById(dto.getId())) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        Category entity = categoryMapper.toCategory(dto);
        entity.setId(id);
        return categoryMapper.toCategoryResponseDTO(categoryRepository.save(entity));
    }

    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponseDTO getCategory(UUID id) throws AppException {
        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryResponseDTOsFromEntities(categories);
    }

    @Override
    public CategoryResponseDTO getCategoryById(UUID categoryId) throws AppException {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return categoryMapper.toCategoryResponseDTO(category);
    }

    @Override
    public Category getCategoryByName(String name) throws AppException {
        return categoryRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
