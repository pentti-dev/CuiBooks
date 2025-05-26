package com.example.mobileapi.entity;

import com.example.mobileapi.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Table(name = "customers")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;

    @Column(nullable = false)
    String fullname;

    @Column(unique = true, nullable = false)
    String username;
    @Column(unique = true, length = 100, nullable = false)
    String email;

    @Column(nullable = false, name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    @Column(nullable = false, name = "number_phone")
    String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Cart cart;

    @Column(name = "reset_code")
    String resetCode;
    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Order> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Customer customer = (Customer) o;

        return id != null ? id.equals(customer.id) : customer.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
