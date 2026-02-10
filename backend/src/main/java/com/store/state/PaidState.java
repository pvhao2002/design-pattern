package com.store.state;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class PaidState extends AbstractOrderState {

    @Override
    public void ship(Order order) {
        setStatus(order, OrderStatus.SHIPPED);
    }

    @Override
    public void cancel(Order order) {
        setStatus(order, OrderStatus.CANCELLED);
    }
}
