package vn.edu.hcmuaf.fit.fahabook.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CategoryResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.service.CategoryService;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CategoryController {

    CategoryService categoryService;

    @GetMapping("/list")
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryResponseDTO getCategory(@PathVariable UUID categoryId) throws AppException {
        return categoryService.getCategory(categoryId);
    }
}
