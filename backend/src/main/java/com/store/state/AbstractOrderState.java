package com.store.state;

import com.store.entity.Order;
import com.store.entity.OrderStatus;

/**
 * Base class cho State pattern — mặc định no-op, subclass override.
 */
public abstract class AbstractOrderState implements OrderState {

    @Override
    public void confirm(Order order) {
        throw new IllegalStateException("Cannot confirm from " + order.getStatus());
    }

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Cannot pay from " + order.getStatus());
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship from " + order.getStatus());
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver from " + order.getStatus());
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel from " + order.getStatus());
    }

    protected void setStatus(Order order, OrderStatus status) {
        order.setStatus(status);
    }
}
