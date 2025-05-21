package com.example.mobileapi.repository;

import com.example.mobileapi.dto.projection.MonthlyRevenueProjection;
import com.example.mobileapi.entity.Order;
import com.example.mobileapi.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerId(UUID customerId);

    @Query(value = "SELECT\n" +
            "    months.Month AS Month,\n" +
            "    COALESCE(SUM(orders.total_amount), 0) AS Monthly_Revenue\n" +
            "FROM\n" +
            "    (SELECT 1 AS Month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\n" +
            "     UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8\n" +
            "     UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) AS months\n" +
            "LEFT JOIN\n" +
            "    orders ON months.Month = MONTH(orders.order_date)\n" +
            "          AND YEAR(orders.order_date) = YEAR(CURDATE())\n" +
            "GROUP BY\n" +
            "    months.Month\n" +
            "ORDER BY\n" +
            "    months.Month;", nativeQuery = true)
    List<MonthlyRevenueProjection> getMonthlyRevenue();

    List<Order> findByStatusAndCustomer_Id(OrderStatus status, UUID customerId);

    List<Order> findByStatus(OrderStatus status);
}
