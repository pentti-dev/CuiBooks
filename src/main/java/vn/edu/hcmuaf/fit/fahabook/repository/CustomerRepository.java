package vn.edu.hcmuaf.fit.fahabook.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.fahabook.entity.Customer;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.CustomerStatus;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Customer u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    Optional<Customer> findByUsername(String username);

    @Query("SELECT COALESCE(SUM(ci.quantity), 0) AS total_quantity " + "FROM CartItem ci "
            + "WHERE ci.cart.id IN (SELECT c.id FROM Cart c WHERE c.customer.id = :customerId)")
    int getQuantityByCustomerId(@Param("customerId") UUID customerId);

    boolean existsByEmail(String email);

    @Query("SELECT u.id FROM Customer u WHERE u.username = :username")
    UUID findIdByUsername(String username);

    Optional<Customer> findByEmail(String email);

    boolean existsByPhone(String phone);


    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.status = :status WHERE c.id = :id")
    void changeStatusById(@Param("id") UUID id, @Param("status") CustomerStatus status);
}
