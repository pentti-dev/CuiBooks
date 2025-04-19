package com.example.mobileapi.graphql.fieldresolver;

import com.example.mobileapi.entity.Category;
import com.example.mobileapi.entity.Product;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ProductFieldResolver {

//    ProductRepository productRepository;

    @SchemaMapping(typeName = "Product", field = "category")
    public Category resolveCategory(Product product) throws AppException {
        Category category = product.getCategory();
        if (category == null) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return category;
    }

}


