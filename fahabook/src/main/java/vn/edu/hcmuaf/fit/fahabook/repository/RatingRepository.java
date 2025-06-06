package vn.edu.hcmuaf.fit.fahabook.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hcmuaf.fit.fahabook.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {}
