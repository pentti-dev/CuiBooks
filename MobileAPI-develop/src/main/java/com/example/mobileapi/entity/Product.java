package com.example.mobileapi.entity;

import com.example.mobileapi.entity.enums.BookForm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
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
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    String supplier; // nhà cung cấp

    String author; // tác giả

    Integer publishYear; // năm xuất bản

    String publisher; // nhà xuất bản

    Integer weight; // trọng lượng (gram)

    String size; // kích thước

    Integer pageNumber; // số trang

    @Enumerated(EnumType.STRING)
    BookForm form; // hình thức
@Column(columnDefinition = "integer default 0")
    Integer stock =0;

    @Column(nullable = false)
    double discount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Rating> ratings = new ArrayList<>();

}
