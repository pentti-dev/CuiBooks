package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CreateAddressDto;
import com.example.mobileapi.dto.request.UpdateAddressDto;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.entity.Address;

import java.util.List;
import java.util.UUID;

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
