package com.example.mobileapi.repository;

import com.example.mobileapi.dto.response.MonthlyRevenueResponse;
import com.example.mobileapi.dto.response.OrderResponseDTO;
import com.example.mobileapi.model.Cart;
import com.example.mobileapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerId(int customerId);

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
    List<Object[]> getMonthlyRevenue();

    List<Order> findByStatus(String status);

    List<Order> findByStatusAndCustomerId(String status, int customerId);
}
