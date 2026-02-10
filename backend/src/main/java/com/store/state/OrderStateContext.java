package com.store.state;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Design pattern: State — context chọn OrderState theo status.
 */
@Component
public class OrderStateContext {

    private final Map<OrderStatus, OrderState> states = new EnumMap<>(OrderStatus.class);

    public OrderStateContext(DraftState draft, ConfirmedState confirmed, PaidState paid,
                             ShippedState shipped, DeliveredState delivered) {
        states.put(OrderStatus.DRAFT, draft);
        states.put(OrderStatus.CONFIRMED, confirmed);
        states.put(OrderStatus.PAID, paid);
        states.put(OrderStatus.SHIPPED, shipped);
        states.put(OrderStatus.DELIVERED, delivered);
        states.put(OrderStatus.CANCELLED, new AbstractOrderState() {});
    }

    public OrderState getState(Order order) {
        if (order == null) return states.get(OrderStatus.DRAFT);
        OrderStatus status = order.getStatus();
        return status != null ? states.getOrDefault(status, states.get(OrderStatus.DRAFT)) : states.get(OrderStatus.DRAFT);
    }
}
