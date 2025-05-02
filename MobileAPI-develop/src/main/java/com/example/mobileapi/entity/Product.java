package com.example.mobileapi.entity;

import com.example.mobileapi.entity.enums.BookForm;
import com.example.mobileapi.entity.enums.Language;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String img;

    @Column(nullable = false)
    BigDecimal price;

    @Lob
    @Column(columnDefinition = "TEXT")
    String detail;

    @Column(nullable = false)
    String supplier; // nhà cung cấp

    @Column(nullable = false)
    String author; // tác giả

    @Column(nullable = false)
    Integer publishYear; // năm xuất bản

    @Column(nullable = false)
    String publisher; // nhà xuất bản

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Language language; // ngôn ngữ

    @Column(nullable = false)
    Integer weight; // trọng lượng (gram)

    @Column(nullable = false)
    String size; // kích thước

    @Column(nullable = false)
    Integer pageNumber; // số trang

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    BookForm form; // hình thức

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    Category category;

    // Bắt buộc phải để kèm "= new ArrayList<>()" khi dùng @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    List<OrderDetail> orderDetails = new ArrayList<>();
}
