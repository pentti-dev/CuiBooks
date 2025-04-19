package com.example.mobileapi.entity;

import com.example.mobileapi.entity.enums.BookForm;
import com.example.mobileapi.entity.enums.Language;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Table(name = "products")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String img;

    @Column(nullable = false)
    int price;


    @Lob
    @Column(columnDefinition = "TEXT")
    String detail;

    @Column(nullable = false)
    String supplier;//nhà cung cấp

    @Column(nullable = false)
    String author;//tác giả

    @Column(nullable = false)
    Integer publishYear;//năm xuất bản

    @Column(nullable = false)
    String publisher;//nhà xuất bản

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Language language;//ngôn ngữ

    @Column(nullable = false)
    Byte weight;//trọng lượng

    @Column(nullable = false)
    String size;//kích thước

    @Column(nullable = false)
    Integer pageNumber;//số trang

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BookForm form;//hình thức

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails = new ArrayList<>();
}

