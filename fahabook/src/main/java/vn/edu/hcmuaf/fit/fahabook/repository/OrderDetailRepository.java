package vn.edu.hcmuaf.fit.fahabook.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.hcmuaf.fit.fahabook.entity.Order;
import vn.edu.hcmuaf.fit.fahabook.entity.OrderDetail;
import vn.edu.hcmuaf.fit.fahabook.entity.Product;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> findOrderByOrderId(UUID orderId);

    OrderDetail findOrderByProductId(UUID productId);

    @Query("SELECT od.product FROM OrderDetail od WHERE od.order.id = :orderId")
    Product findProductByOrderId(UUID orderId);

    @Query("SELECT od.order FROM OrderDetail od WHERE od.id = :orderDetailId")
    Order findOrderIdByOrderDetailId(UUID orderDetailId);

    List<OrderDetail> findByOrderId(UUID orderId);
}
