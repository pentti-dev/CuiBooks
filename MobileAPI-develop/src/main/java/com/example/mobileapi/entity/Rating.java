package com.example.mobileapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ratings")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String comment;
    @Max(5)
    @Min(1)
    Integer score;

    @Column(name = "created_at")
    Instant createdAt;
    @Column(name = "updated_at")
    Instant updatedAt;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
    @Transient
    Double avgScore;

}
