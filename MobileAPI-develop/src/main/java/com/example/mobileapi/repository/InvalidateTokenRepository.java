package com.example.mobileapi.repository;

import com.example.mobileapi.entity.InvalidateToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken, String> {
    boolean existsById(String id);
}
