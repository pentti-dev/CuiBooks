package vn.edu.hcmuaf.fit.fahabook.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.edu.hcmuaf.fit.fahabook.dto.request.create.ProductCreateDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.ProductUpdateDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ProductResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Mapping từ ProductCreateDTO -> Product
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(source = "categoryId", target = "category.id") // ánh xạ categoryId sang category.id
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Product toProduct(ProductCreateDTO dto);

    // Mapping từ ProductUpdateDTO -> Product
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(source = "categoryId", target = "category.id") // ánh xạ categoryId sang category.id
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Product toProduct(ProductUpdateDTO dto);

    // Mapping từ ProductResponseDTO -> Product
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Product toProduct(ProductResponseDTO dto);

    // Mapping từ Product -> ProductCreateDTO
    @Mapping(source = "category.id", target = "categoryId")
    ProductCreateDTO toProductRequestDTO(Product product);

    // Mapping từ Product -> ProductResponseDTO
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "discount", target = "discount")
    ProductResponseDTO toProductResponseDTO(Product product);

    List<ProductResponseDTO> toProductResponseDTOList(List<Product> productList);

    List<Product> toProductList(List<ProductResponseDTO> dtos);
}
