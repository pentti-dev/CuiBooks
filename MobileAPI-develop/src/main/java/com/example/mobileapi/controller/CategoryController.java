package com.example.mobileapi.controller;

import com.example.mobileapi.dto.response.CategoryResponseDTO;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CategoryResponseDTO getCategory(@PathVariable int categoryId) throws AppException {
        return categoryService.getCategory(categoryId);
    }
}
