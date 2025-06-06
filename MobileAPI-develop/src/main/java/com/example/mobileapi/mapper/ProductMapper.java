package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.create.ProductCreateDTO;
import com.example.mobileapi.dto.request.update.ProductUpdateDTO;
import com.example.mobileapi.dto.response.ProductResponseDTO;
import com.example.mobileapi.entity.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Product toProduct(ProductCreateDTO dto);

    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Product toProduct(ProductUpdateDTO dto);

    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Product toProduct(ProductResponseDTO dto);

    @Mapping(source = "category.id", target = "categoryId")
    ProductCreateDTO toProductRequestDTO(Product product);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "discount", target = "discount")
    ProductResponseDTO toProductResponseDTO(Product product);
    List<ProductResponseDTO> toProductResponseDTOList(List<Product> productList);

    List<Product> toProductList(List<ProductResponseDTO> dtos);
}
