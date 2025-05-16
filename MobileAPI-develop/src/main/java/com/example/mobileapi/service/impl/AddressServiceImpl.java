package com.example.mobileapi.service.impl;

import com.example.mobileapi.dto.request.CreateAddressDto;
import com.example.mobileapi.dto.request.UpdateAddressDto;
import com.example.mobileapi.exception.AppException;
import com.example.mobileapi.exception.ErrorCode;
import com.example.mobileapi.entity.Address;
import com.example.mobileapi.repository.AddressRepository;
import com.example.mobileapi.repository.CustomerRepository;
import com.example.mobileapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    AddressRepository addressRepository;
    CustomerRepository customerRepository;


    @Override
    public Address createAddress(CreateAddressDto dto) throws AppException {
        Address address = Address.builder()
                .address(dto.getAddress())
                .numberPhone(dto.getNumberPhone())
                .receiver(dto.getReceiver())
                .note(dto.getNote())
                .customer(customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)))
                .isDefault(false)
                .build();
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(UUID id, UpdateAddressDto dto) throws AppException {
        Address address = addressRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        address.setAddress(dto.getAddress());
        address.setNumberPhone(dto.getNumberPhone());
        address.setReceiver(dto.getReceiver());
        address.setNote(dto.getNote());
        address.setCustomer(customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(UUID id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address getAddress(UUID id) throws AppException {
        return addressRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
    }

    @Override
    public List<Address> getAddressByCustomerId(UUID customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }


    @Override
    @Transactional
    public boolean setDefaultAddress(UUID customerId, UUID addressId) {
        List<Address> addresses = addressRepository.findByCustomerId(customerId);
        Optional<Address> targetAddressOpt = addresses.stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst();
        if (!targetAddressOpt.isPresent()) {
            return false;
        }
        addresses.forEach(address -> address.setDefault(false));
        Address targetAddress = targetAddressOpt.get();
        targetAddress.setDefault(true);
        addressRepository.saveAll(addresses);
        return true;
    }

    @Override
    public Address getAddressByCustomerIdAndIsDefault(UUID customerId) {
        return addressRepository.findByCustomerIdAndIsDefault(customerId);
    }


}
