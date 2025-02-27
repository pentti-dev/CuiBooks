package com.example.mobileapi.repository;

import com.example.mobileapi.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartId(int cartId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    CartItem findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") int cartId);

}
