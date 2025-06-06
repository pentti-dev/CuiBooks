package vn.edu.hcmuaf.fit.fahabook.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hcmuaf.fit.fahabook.entity.Cart;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomerId(UUID customerId);

    Optional<Cart> findByCustomer(Customer customer);

    Optional<Cart> findCartByCustomer_Username(String customerUsername);
}
