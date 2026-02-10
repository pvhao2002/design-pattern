package com.store.memento;

import com.store.entity.Order;
import com.store.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Design pattern: Memento — CareTaker tạo và khôi phục snapshot đơn hàng.
 */
@Component
public class OrderMementoCareTaker {

    public OrderMemento save(Order order) {
        if (order == null) return null;
        List<OrderMemento.OrderItemMemento> items = new ArrayList<>();
        if (order.getItems() != null) {
            items = order.getItems().stream()
                    .map(i -> new OrderMemento.OrderItemMemento(
                            i.getProduct() != null ? i.getProduct().getId() : null,
                            i.getQuantity() != null ? i.getQuantity() : 0,
                            i.getUnitPrice() != null ? i.getUnitPrice() : BigDecimal.ZERO))
                    .collect(Collectors.toList());
        }
        return new OrderMemento(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                items);
    }

    public void restoreStatus(Order order, OrderMemento memento) {
        if (order != null && memento != null) {
            order.setStatus(memento.getStatus());
        }
    }
}
