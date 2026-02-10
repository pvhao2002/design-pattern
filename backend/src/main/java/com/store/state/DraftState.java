package com.store.state;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class DraftState extends AbstractOrderState {

    @Override
    public void confirm(Order order) {
        setStatus(order, OrderStatus.CONFIRMED);
    }

    @Override
    public void cancel(Order order) {
        setStatus(order, OrderStatus.CANCELLED);
    }
}
