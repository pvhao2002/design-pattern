package com.store.observer;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import org.springframework.context.ApplicationEvent;

/**
 * Design pattern: Observer — event khi trạng thái đơn thay đổi.
 */
public class OrderStatusChangedEvent extends ApplicationEvent {

    private final Order order;
    private final OrderStatus previousStatus;

    public OrderStatusChangedEvent(Object source, Order order, OrderStatus previousStatus) {
        super(source);
        this.order = order;
        this.previousStatus = previousStatus;
    }

    public Order getOrder() { return order; }
    public OrderStatus getPreviousStatus() { return previousStatus; }
}
