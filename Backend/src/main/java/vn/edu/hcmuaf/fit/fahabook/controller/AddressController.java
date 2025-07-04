package vn.edu.hcmuaf.fit.fahabook.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CreateAddressDto;
import vn.edu.hcmuaf.fit.fahabook.dto.request.UpdateAddressDto;
import vn.edu.hcmuaf.fit.fahabook.entity.Address;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.service.AddressService;

@RestController
@RequestMapping("/api/address")
@Tag(name = "Address", description = "Address API")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/")
    public List<Address> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/customer/{customerId}")
    public List<Address> getAddressByCustomerId(@PathVariable UUID customerId) {
        return addressService.getAddressByCustomerId(customerId);
    }

    @GetMapping("/{id}")
    public Address getAddress(@PathVariable UUID id) throws AppException {
        return addressService.getAddress(id);
    }

    @PostMapping("/")
    public Address createAddress(@RequestBody CreateAddressDto dto) {
        try {
            return addressService.createAddress(dto);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable UUID id, @RequestBody UpdateAddressDto dto) {
        try {
            return addressService.updateAddress(id, dto);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
    }

    @PatchMapping("/default/{customerId}/{addressId}")
    public boolean setDefaultAddress(@PathVariable UUID customerId, @PathVariable UUID addressId) {
        return addressService.setDefaultAddress(customerId, addressId);
    }

    @GetMapping("/default/{customerId}")
    public Address getAddressByCustomerIdAndIsDefault(@PathVariable UUID customerId) {
        return addressService.getAddressByCustomerIdAndIsDefault(customerId);
    }
}
