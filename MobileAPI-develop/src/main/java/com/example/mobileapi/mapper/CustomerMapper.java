package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.entity.Cart;
import com.example.mobileapi.entity.Customer;
import com.example.mobileapi.entity.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequestDTO request);

    //
    @Mapping(target = "role", source = "role", qualifiedByName = "booleanToRole")
    @Mapping(target = "cartId", source = "cart", qualifiedByName = "cartToCartId")
    CustomerResponseDTO toCustomerResponse(Customer customer);

    @Named("booleanToRole")
    static Role booleanToRole(boolean role) {
        return Role.role(role);
    }

    @Named("cartToCartId")
    static Integer cartToCartId(Cart cart) {
        return (cart != null) ? cart.getId() : null;
    }

    void updateCustomerFromDto(CustomerRequestDTO dto, @MappingTarget Customer entity);
}
