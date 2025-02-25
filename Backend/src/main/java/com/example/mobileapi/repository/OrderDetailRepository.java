package com.example.mobileapi.repository;

import com.example.mobileapi.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findOrderByOrderId(int orderId);

    OrderDetail findOrderByProductId(int productId);
}
