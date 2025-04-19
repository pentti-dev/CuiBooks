package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.ProductRequestDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequestDTO dto);

    Product toProduct(ProductResponseDTO dto);

    ProductRequestDTO toProductRequestDTO(Product product);

    @Mapping(source = "language.displayName", target = "language")
    @Mapping(source = "form.displayName", target = "form")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDTO toProductResponseDTO(Product product);

    List<ProductResponseDTO> toProductResponseDTOList(List<Product> productList);

    List<Product> toProductList(List<ProductResponseDTO> dtos);
}
