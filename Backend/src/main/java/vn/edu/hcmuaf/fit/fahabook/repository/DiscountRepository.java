package vn.edu.hcmuaf.fit.fahabook.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.hcmuaf.fit.fahabook.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    Optional<Discount> findByCode(String code);

    List<Discount> getAllByActive(Boolean active);

    Optional<UUID> findIdByCode(String code);

    boolean existsByCode(String code);
}
