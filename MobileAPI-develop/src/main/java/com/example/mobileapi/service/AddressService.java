package com.example.mobileapi.service;

import com.example.mobileapi.dto.request.CreateAddressDto;
import com.example.mobileapi.dto.request.UpdateAddressDto;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.model.Address;
import com.example.mobileapi.model.Customer;

import java.util.List;

public interface AddressService {

    Address createAddress(CreateAddressDto dto) throws AppException;

    Address updateAddress(Integer id, UpdateAddressDto dto) throws AppException;

    void deleteAddress(Integer id);

    Address getAddress(Integer id) throws AppException;

    List<Address> getAddressByCustomerId(Integer customerId);

    List<Address> getAllAddress();

    boolean setDefaultAddress(Integer customerId, Integer addressId);

    Address getAddressByCustomerIdAndIsDefault(Integer customerId);

}
