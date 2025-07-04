package vn.edu.hcmuaf.fit.fahabook.graphql.fieldresolver;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.edu.hcmuaf.fit.fahabook.entity.Category;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ProductFieldResolver {

    @SchemaMapping(typeName = "Product", field = "category")
    public Category resolveCategory(Product product) throws AppException {
        Category category = product.getCategory();
        if (category == null) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return category;
    }
}
