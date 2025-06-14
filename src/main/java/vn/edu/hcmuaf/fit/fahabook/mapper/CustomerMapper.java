package vn.edu.hcmuaf.fit.fahabook.mapper;

import java.util.UUID;

import org.mapstruct.*;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CustomerRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CustomerResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Cart;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequestDTO request);

    @Mapping(target = "cartId", source = "cart", qualifiedByName = "cartToCartId")
    @Mapping(target = "password", source = "password")
    CustomerResponseDTO toCustomerResponse(Customer customer);

    @Named("cartToCartId")
     static UUID cartToCartId(Cart cart) {
        return (cart != null) ? cart.getId() : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(CustomerRequestDTO dto, @MappingTarget Customer entity);
}
