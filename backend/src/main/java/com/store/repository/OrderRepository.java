package com.store.repository;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = :status AND o.createdAt BETWEEN :from AND :to")
    BigDecimal sumTotalByStatusAndDateBetween(
            @Param("status") OrderStatus status,
            @Param("from") Instant from,
            @Param("to") Instant to);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = com.store.entity.OrderStatus.DELIVERED AND o.createdAt BETWEEN :from AND :to")
    BigDecimal sumDeliveredRevenueBetween(@Param("from") Instant from, @Param("to") Instant to);
}
