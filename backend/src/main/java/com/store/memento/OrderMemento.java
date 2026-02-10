package com.store.memento;

import com.store.entity.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Design pattern: Memento — snapshot trạng thái đơn hàng để lưu/khôi phục (undo).
 */
public class OrderMemento implements Serializable {

    private final Long orderId;
    private final OrderStatus status;
    private final BigDecimal totalAmount;
    private final List<OrderItemMemento> items = new ArrayList<>();

    public OrderMemento(Long orderId, OrderStatus status, BigDecimal totalAmount,
                       List<OrderItemMemento> items) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        if (items != null) this.items.addAll(items);
    }

    public Long getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItemMemento> getItems() { return new ArrayList<>(items); }

    public record OrderItemMemento(Long productId, int quantity, BigDecimal unitPrice) implements Serializable {}
}
