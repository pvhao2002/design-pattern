package com.store.command;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import com.store.state.OrderStateContext;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Command — lệnh hủy đơn.
 */
@Component
public class CancelOrderCommand implements OrderCommand {

    private final OrderStateContext stateContext;
    private Order order;
    private OrderStatus statusBefore;

    public CancelOrderCommand(OrderStateContext stateContext) {
        this.stateContext = stateContext;
    }

    public CancelOrderCommand setOrder(Order order) {
        this.order = order;
        this.statusBefore = order != null ? order.getStatus() : null;
        return this;
    }

    @Override
    public Order execute() {
        if (order == null) throw new IllegalStateException("Order not set");
        stateContext.getState(order).cancel(order);
        return order;
    }

    @Override
    public void undo() {
        if (order != null && statusBefore != null) {
            order.setStatus(statusBefore);
        }
    }
}
