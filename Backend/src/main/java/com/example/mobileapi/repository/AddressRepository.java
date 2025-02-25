package com.example.mobileapi.repository;

import com.example.mobileapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId")
    List<Address> findByCustomerId(Integer customerId);

    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId AND a.isDefault = true")
    Address findByCustomerIdAndIsDefault(Integer customerId);
}
