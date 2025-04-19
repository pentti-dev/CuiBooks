package com.example.mobileapi.graphql.fieldresolver;

import com.example.mobileapi.entity.Category;
import com.example.mobileapi.entity.Product;
import com.example.mobileapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CategoryFieldResolver {

    ProductRepository productRepository;

    @SchemaMapping(typeName = "Category", field = "products")
    public List<Product> resolveProducts(Category category) {
        return productRepository.findAllByCategoryId(category.getId());
    }
}