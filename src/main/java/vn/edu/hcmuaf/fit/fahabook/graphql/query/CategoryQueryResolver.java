package vn.edu.hcmuaf.fit.fahabook.graphql.query;

import java.util.List;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.entity.Category;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.mapper.CategoryMapper;
import vn.edu.hcmuaf.fit.fahabook.service.CategoryService;

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
