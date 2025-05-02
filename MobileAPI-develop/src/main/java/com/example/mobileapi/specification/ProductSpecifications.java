package com.example.mobileapi.specification;

import com.example.mobileapi.entity.Product;
import com.example.mobileapi.entity.enums.BookForm;
import com.example.mobileapi.entity.enums.Language;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@UtilityClass
public class ProductSpecifications {

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasCategoryId(UUID categoryId) {
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasLanguage(Language language) {
        return (root, query, cb) -> cb.equal(root.get("language"), language);
    }

    public static Specification<Product> hasForm(BookForm form) {
        return (root, query, cb) -> cb.equal(root.get("form"), form);
    }

    public static Specification<Product> priceBetween(Integer minPrice, Integer maxPrice) {
        return (root, query, cb) -> cb.between(root.get("price"), minPrice, maxPrice);
    }
}

