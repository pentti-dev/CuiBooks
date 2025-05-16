package com.example.mobileapi.repository;

import com.example.mobileapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId")
    List<Address> findByCustomerId(UUID customerId);

    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId AND a.isDefault = true")
    Address findByCustomerIdAndIsDefault(UUID customerId);
}
