package com.example.mobileapi.repository;

import com.example.mobileapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Customer u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT u  from Customer u WHERE u.username = :username and u.password = :password ")
    Optional<Customer> login(@Param("username") String username, @Param("password") String password);

    @Query("SELECT u  from Customer u WHERE u.username = :username")
    Optional<Customer> findByUsername(String username);

    @Query("SELECT COALESCE(SUM(ci.quantity), 0) AS total_quantity " + "FROM CartItem ci " + "WHERE ci.cart.id IN (SELECT c.id FROM Cart c WHERE c.customer.id = :customerId)")
    int getQuantityByCustomerId(@Param("customerId") UUID customerId);

    boolean existsByEmail(String email);

    @Query("SELECT u.id FROM Customer u WHERE u.username = :username")
    UUID findIdByUsername(String username);

    Optional<Customer> findByEmail(String email);
}
