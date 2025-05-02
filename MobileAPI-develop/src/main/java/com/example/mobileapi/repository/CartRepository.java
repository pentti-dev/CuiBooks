package com.example.mobileapi.repository;

import com.example.mobileapi.entity.Cart;
import com.example.mobileapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomerId(UUID customerId);

    Optional<Cart> findByCustomer(Customer customer);

    Optional<Cart> findCartByCustomer_Username(String customerUsername);
}
