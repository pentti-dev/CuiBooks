package com.example.mobileapi.repository;

import com.example.mobileapi.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    Optional<Discount> findByCode(String code);

    List<Discount> getAllByActive(Boolean active);

    Optional<UUID> findIdByCode(String code);

}
