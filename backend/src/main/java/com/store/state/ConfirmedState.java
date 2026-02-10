package com.store.state;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class ConfirmedState extends AbstractOrderState {

    @Override
    public void pay(Order order) {
        setStatus(order, OrderStatus.PAID);
    }

    @Override
    public void cancel(Order order) {
        setStatus(order, OrderStatus.CANCELLED);
    }
}
