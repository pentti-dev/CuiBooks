package vn.edu.hcmuaf.fit.fahabook.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.hcmuaf.fit.fahabook.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId")
    List<Address> findByCustomerId(UUID customerId);

    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId AND a.isDefault = true")
    Address findByCustomerIdAndIsDefault(UUID customerId);
}
