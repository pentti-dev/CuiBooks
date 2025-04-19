package com.example.mobileapi.graphql.mutation;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')")
public class ProductMutationResolver {

    ProductService productService;

    // Thêm sản phẩm
    @MutationMapping
    public ProductResponseDTO addProduct(@Argument("input") ProductRequestDTO product) {
        return productService.saveProduct(product);
    }

    // Cập nhật sản phẩm
    @MutationMapping
    public ProductResponseDTO updateProduct(@Argument("id") int id, @Argument("input") ProductRequestDTO product) {
        return productService.updateProduct(id, product);
    }

    // Xóa sản phẩm
    @MutationMapping
    public Boolean deleteProduct(@Argument int id) {
        productService.deleteProduct(id);
        return Boolean.TRUE;
    }
}
