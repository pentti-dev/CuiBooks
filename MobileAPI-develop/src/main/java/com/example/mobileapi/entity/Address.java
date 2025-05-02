package com.example.mobileapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "addresses")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "number_phone", nullable = false)
    private String numberPhone;

    @Column(name = "receiver", nullable = false)
    private String receiver;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

}
