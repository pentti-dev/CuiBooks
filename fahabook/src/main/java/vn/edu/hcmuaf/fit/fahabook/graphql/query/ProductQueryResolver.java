package vn.edu.hcmuaf.fit.fahabook.graphql.query;

import java.util.List;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.dto.request.ProductFilter;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.mapper.ProductMapper;
import vn.edu.hcmuaf.fit.fahabook.service.ProductService;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductQueryResolver {

    ProductService productService;
    ProductMapper productMapper;

    @QueryMapping
    public List<Product> products() {
        return productMapper.toProductList(productService.getAllProducts());
    }

    @QueryMapping
    public Product productById(@Argument UUID id) throws AppException {
        return productMapper.toProduct(productService.getProductById(id));
    }

    @QueryMapping
    public List<Product> productsByName(@Argument String name) {
        return productMapper.toProductList(productService.getProductByName(name));
    }

    @QueryMapping
    public List<Product> productsByCategoryId(@Argument UUID categoryId) {
        return productMapper.toProductList(productService.findByCategoryId(categoryId));
    }

    @QueryMapping
    public List<Product> filteredProducts(@Argument("filter") ProductFilter filter) {

        return productMapper.toProductList(productService.filterProducts(filter));
    }
}
