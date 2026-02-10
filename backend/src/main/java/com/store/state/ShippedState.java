package com.store.state;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class ShippedState extends AbstractOrderState {

    @Override
    public void deliver(Order order) {
        setStatus(order, OrderStatus.DELIVERED);
    }
}
