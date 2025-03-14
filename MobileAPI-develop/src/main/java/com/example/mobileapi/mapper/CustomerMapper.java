package com.example.mobileapi.mapper;

import com.example.mobileapi.dto.request.CustomerRequestDTO;
import com.example.mobileapi.dto.response.CustomerResponseDTO;
import com.example.mobileapi.model.Customer;
import com.example.mobileapi.model.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CustomerRequestDTO request);

    @Mapping(target = "role", source = "role", qualifiedByName = "booleanToRole")
    CustomerResponseDTO toCustomerResponse(Customer customer);

    @Named("booleanToRole")
    static Role booleanToRole(boolean role) {
        return Role.role(role);
    }
}
