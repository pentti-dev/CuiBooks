package vn.edu.hcmuaf.fit.fahabook.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.hcmuaf.fit.fahabook.entity.Order;
import vn.edu.hcmuaf.fit.fahabook.entity.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerId(UUID customerId);

    List<Order> findByStatusAndCustomer_Id(OrderStatus status, UUID customerId);

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT o.totalAmount FROM Order o WHERE o.id = ?1")
    BigDecimal findTotalAmountByOrderId(UUID orderId);

    List<Order> findAllByOrderDateBetween(LocalDateTime orderDateAfter, LocalDateTime orderDateBefore);
}
