package vn.edu.hcmuaf.fit.fahabook.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.fahabook.dto.request.create.CreateCustomerDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.update.UpdateCustomerDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.response.CustomerResponseDTO;
import vn.edu.hcmuaf.fit.fahabook.entity.Cart;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CreateCustomerDTO request);

    Customer toCustomer(UpdateCustomerDTO request);

    @Mapping(target = "cartId", source = "cart", qualifiedByName = "cartToCartId")
    @Mapping(target = "password", source = "password")
    CustomerResponseDTO toCustomerResponse(Customer customer);

    @Named("cartToCartId")
    static UUID cartToCartId(Cart cart) {
        return (cart != null) ? cart.getId() : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCustomerFromDto(UpdateCustomerDTO dto, @MappingTarget Customer entity);


}
