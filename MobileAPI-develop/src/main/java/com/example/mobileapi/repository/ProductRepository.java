package com.example.mobileapi.repository;

import com.example.mobileapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    List<Product> findByCategoryId(UUID categoryId);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findAllByCategoryId(UUID categoryId);


}
