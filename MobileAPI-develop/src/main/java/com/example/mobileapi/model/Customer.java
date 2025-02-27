package com.example.mobileapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    Integer id;

    @Column(nullable = false, name = "fullname")
    String fullname;

    @Column(nullable = false, name = "username")
    String username;

    @Column(unique = true, length = 100, nullable = false, name = "email")
    String email;

    @Column(nullable = false, name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Column(nullable = false, name = "number_phone")
    String phone;
    // 0-customer, 1-admin
    boolean role;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Cart> carts = new ArrayList<>();

    @Column(name = "reset_code")
    String resetCode;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Order> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id != null ? id.equals(customer.id) : customer.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}

