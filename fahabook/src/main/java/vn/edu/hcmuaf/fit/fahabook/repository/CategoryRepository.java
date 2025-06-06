package vn.edu.hcmuaf.fit.fahabook.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hcmuaf.fit.fahabook.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);

    Optional<Category> findByCode(String code);

    boolean existsCategoryByCodeOrName(String code, String name);
}
