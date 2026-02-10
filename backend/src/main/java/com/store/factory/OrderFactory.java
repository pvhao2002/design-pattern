package com.store.factory;

import com.store.entity.Customer;
import com.store.entity.Order;
import com.store.entity.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Design pattern: Factory — tạo Order (và có thể thêm items qua OrderItemFactory).
 */
@Component
public class OrderFactory {

    private final OrderItemFactory orderItemFactory;

    public OrderFactory(OrderItemFactory orderItemFactory) {
        this.orderItemFactory = orderItemFactory;
    }

    public Order create() {
        return create(null);
    }

    public Order create(Customer customer) {
        return Order.builder()
                .code("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .customer(customer)
                .status(OrderStatus.DRAFT)
                .build();
    }
}
