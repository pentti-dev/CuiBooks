package com.example.mobileapi.graphql;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProductMutationResolver {

    ProductService productService;

    @MutationMapping
    public int addProduct(@Argument("product") ProductRequestDTO product) {
        // Chuyển đổi ProductInput thành ProductRequestDTO
        return productService.saveProduct(product);
    }

    @MutationMapping
    public ProductResponseDTO updateProduct(@Argument int id, @Argument("product") ProductRequestDTO product) {
        productService.updateProduct(id, product);
        return productService.getProductById(id);
    }

    @MutationMapping
    public String deleteProduct(@Argument int id) {
        productService.deleteProduct(id);
        return "Xóa sản phẩm thành công";
    }
}
