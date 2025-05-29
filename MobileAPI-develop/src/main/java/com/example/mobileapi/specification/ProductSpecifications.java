package com.example.mobileapi.specification;

import com.example.mobileapi.entity.Product;
import com.example.mobileapi.entity.enums.BookForm;
import jakarta.persistence.criteria.Path;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class ProductSpecifications {

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> detailContains(String detail) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("detail")), "%" + detail.toLowerCase() + "%");
    }

    public static Specification<Product> supplierContains(String supplier) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("supplier")), "%" + supplier.toLowerCase() + "%");
    }

    public static Specification<Product> authorContains(String author) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }

    public static Specification<Product> publisherContains(String publisher) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("publisher")), "%" + publisher.toLowerCase() + "%");
    }

    public static Specification<Product> hasCategoryId(UUID categoryId) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("id"), categoryId);
    }


    public static Specification<Product> hasForm(BookForm form) {
        return (root, query, cb) ->
                cb.equal(root.get("form"), form);
    }

    public static Specification<Product> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            Path<BigDecimal> path = root.get("price");
            if (minPrice != null && maxPrice != null) {
                return cb.between(path, minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(path, minPrice);
            } else if (maxPrice != null) {
                return cb.lessThanOrEqualTo(path, maxPrice);
            } else {
                return cb.conjunction();
            }
        };
    }

    public static Specification<Product> discountBetween(Double minDiscount, Double maxDiscount) {
        return (root, query, cb) -> {
            Path<Double> path = root.get("discount");
            if (minDiscount != null && maxDiscount != null) {
                return cb.between(path, minDiscount, maxDiscount);
            } else if (minDiscount != null) {
                return cb.greaterThanOrEqualTo(path, minDiscount);
            } else if (maxDiscount != null) {
                return cb.lessThanOrEqualTo(path, maxDiscount);
            } else {
                return cb.conjunction();
            }
        };
    }

    public static Specification<Product> publishYearBetween(Integer minYear, Integer maxYear) {
        return (root, query, cb) -> {
            Path<Integer> path = root.get("publishYear");
            if (minYear != null && maxYear != null) {
                return cb.between(path, minYear, maxYear);
            } else if (minYear != null) {
                return cb.greaterThanOrEqualTo(path, minYear);
            } else if (maxYear != null) {
                return cb.lessThanOrEqualTo(path, maxYear);
            } else {
                return cb.conjunction();
            }
        };
    }

    public static Specification<Product> weightBetween(Integer minWeight, Integer maxWeight) {
        return (root, query, cb) -> {
            Path<Integer> path = root.get("weight");
            if (minWeight != null && maxWeight != null) {
                return cb.between(path, minWeight, maxWeight);
            } else if (minWeight != null) {
                return cb.greaterThanOrEqualTo(path, minWeight);
            } else if (maxWeight != null) {
                return cb.lessThanOrEqualTo(path, maxWeight);
            } else {
                return cb.conjunction();
            }
        };
    }

    public static Specification<Product> sizeContains(String size) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("size")), "%" + size.toLowerCase() + "%");
    }

    public static Specification<Product> pageNumberBetween(Integer minPages, Integer maxPages) {
        return (root, query, cb) -> {
            Path<Integer> path = root.get("pageNumber");
            if (minPages != null && maxPages != null) {
                return cb.between(path, minPages, maxPages);
            } else if (minPages != null) {
                return cb.greaterThanOrEqualTo(path, minPages);
            } else if (maxPages != null) {
                return cb.lessThanOrEqualTo(path, maxPages);
            } else {
                return cb.conjunction();
            }
        };
    }
}
