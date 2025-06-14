package vn.edu.hcmuaf.fit.fahabook.graphql.mutation;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CategoryRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CategoryResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.service.CategoryService;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')")
public class CategoryMutationResolver {

    CategoryService categoryService;

    @MutationMapping
    public CategoryResponseDTO addCategory(@Valid @Argument("input") CategoryRequestDTO category) {
        return categoryService.saveCategory(category);
    }

    @MutationMapping
    public CategoryResponseDTO updateCategory(
            @Argument("id") UUID id, @Valid @Argument("input") CategoryRequestDTO category) throws AppException {
        return categoryService.updateCategory(category);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument UUID id) {
        categoryService.deleteCategory(id);
        return Boolean.TRUE;
    }
}
