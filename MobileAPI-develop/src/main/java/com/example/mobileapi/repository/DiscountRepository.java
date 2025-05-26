package com.example.mobileapi.repository;

import com.example.mobileapi.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    Optional<Discount> findByCodeAndActiveIsTrue(String code);

    @Query(value = """
                SELECT * FROM discounts
                WHERE code = :code
                  AND active = true
                  AND CURRENT_TIMESTAMP BETWEEN valid_from AND valid_to
            """, nativeQuery = true)
    Optional<Boolean> checkDiscountTime(String code);

    List<Discount> getAllByActive(Boolean active);

    Optional<UUID> findIdByCode(String code);
}
