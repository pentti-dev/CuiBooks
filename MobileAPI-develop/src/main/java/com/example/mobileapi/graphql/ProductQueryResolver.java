package com.example.mobileapi.graphql;

import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProductQueryResolver {

    ProductService productService;

    @QueryMapping
    public List<ProductResponseDTO> products() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public ProductResponseDTO productById(@Argument Integer id) {
        return productService.getProductById(id);
    }
}
