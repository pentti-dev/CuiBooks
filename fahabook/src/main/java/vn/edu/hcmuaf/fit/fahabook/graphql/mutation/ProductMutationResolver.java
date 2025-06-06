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
import vn.edu.hcmuaf.fit.fahabook.dto.request.create.ProductCreateDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.ProductUpdateDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ProductResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.service.ProductService;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')")
public class ProductMutationResolver {

    ProductService productService;

    // Thêm sản phẩm
    @MutationMapping
    public ProductResponseDTO addProduct(@Valid @Argument("input") ProductCreateDTO product) {
        return productService.saveProduct(product);
    }

    // Cập nhật sản phẩm
    @MutationMapping
    public ProductResponseDTO updateProduct(
            @Argument("id") UUID id, @Valid @Argument("input") ProductUpdateDTO product) {
        return productService.updateProduct(id, product);
    }

    // Xóa sản phẩm
    @MutationMapping
    public Boolean deleteProduct(@Argument UUID id) {
        productService.deleteProduct(id);
        return Boolean.TRUE;
    }
}
