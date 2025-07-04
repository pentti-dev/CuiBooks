package vn.edu.hcmuaf.fit.fahabook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import vn.edu.hcmuaf.fit.fahabook.entity.InvalidateToken;

@Repository
public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken, String> {
    boolean existsById(@NonNull String id);
}
