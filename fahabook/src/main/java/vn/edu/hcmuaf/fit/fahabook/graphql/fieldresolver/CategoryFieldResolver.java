package vn.edu.hcmuaf.fit.fahabook.graphql.fieldresolver;

import java.util.List;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.entity.Category;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;
import vn.edu.hcmuaf.fit.fahabook.repository.ProductRepository;

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
