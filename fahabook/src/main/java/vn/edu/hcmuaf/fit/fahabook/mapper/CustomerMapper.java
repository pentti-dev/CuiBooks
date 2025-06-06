package vn.edu.hcmuaf.fit.fahabook.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CustomerRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CustomerResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Cart;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequestDTO request);

    @Mapping(target = "cartId", source = "cart", qualifiedByName = "cartToCartId")
    CustomerResponseDTO toCustomerResponse(Customer customer);

    @Named("cartToCartId")
    public static UUID cartToCartId(Cart cart) {
        return (cart != null) ? cart.getId() : null;
    }

    void updateCustomerFromDto(CustomerRequestDTO dto, @MappingTarget Customer entity);
}
