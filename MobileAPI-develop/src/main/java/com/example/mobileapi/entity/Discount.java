package com.example.mobileapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

import org.springframework.lang.NonNull;

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

    Date startDate;
    Date endDate;
    @Column(nullable = false)
    Boolean active;
}
