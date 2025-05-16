package com.example.mobileapi.graphql.query;

import com.example.mobileapi.entity.Category;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.mapper.CategoryMapper;
import com.example.mobileapi.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryQueryResolver {

    CategoryService categoryService;
    CategoryMapper categoryMapper;

    @QueryMapping
    public List<Category> categories() {
        return categoryMapper.toCategorieList(categoryService.getAllCategories());
    }

    @QueryMapping
    public Category categoryById(@Argument UUID id) throws AppException {
        return categoryMapper.toCategory(categoryService.getCategoryById(id));
    }
}
