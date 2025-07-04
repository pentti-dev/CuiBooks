package vn.edu.hcmuaf.fit.fahabook.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, unique = true)
    String code;

    @Column(nullable = false)
    Integer percent;

    LocalDate startDate;
    LocalDate endDate;

    @Column(nullable = false)
    Boolean active;
}
