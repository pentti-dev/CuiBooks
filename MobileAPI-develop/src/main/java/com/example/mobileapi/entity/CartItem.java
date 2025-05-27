package com.example.mobileapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Table(name = "cart_items")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "quantity", nullable = false)
    int quantity;

}
