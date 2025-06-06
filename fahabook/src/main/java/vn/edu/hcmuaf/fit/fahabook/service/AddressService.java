package vn.edu.hcmuaf.fit.fahabook.service;

import java.util.List;
import java.util.UUID;

import vn.edu.hcmuaf.fit.fahabook.dto.request.CreateAddressDto;
import vn.edu.hcmuaf.fit.fahabook.dto.request.UpdateAddressDto;
import vn.edu.hcmuaf.fit.fahabook.entity.Address;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;

public interface AddressService {

    Address createAddress(CreateAddressDto dto) throws AppException;

    Address updateAddress(UUID id, UpdateAddressDto dto) throws AppException;

    void deleteAddress(UUID id);

    Address getAddress(UUID id) throws AppException;

    List<Address> getAddressByCustomerId(UUID customerId);

    List<Address> getAllAddress();

    boolean setDefaultAddress(UUID customerId, UUID addressId);

    Address getAddressByCustomerIdAndIsDefault(UUID customerId);
}
