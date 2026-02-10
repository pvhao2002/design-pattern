package com.store.command;

import com.store.entity.Order;
import com.store.state.OrderStateContext;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Command — lệnh xác nhận đơn.
 */
@Component
public class ConfirmOrderCommand implements OrderCommand {

    private final OrderStateContext stateContext;
    private Order order;
    private Order snapshotBefore;

    public ConfirmOrderCommand(OrderStateContext stateContext) {
        this.stateContext = stateContext;
    }

    public ConfirmOrderCommand setOrder(Order order) {
        this.order = order;
        this.snapshotBefore = order != null ? cloneStatus(order) : null;
        return this;
    }

    @Override
    public Order execute() {
        if (order == null) throw new IllegalStateException("Order not set");
        stateContext.getState(order).confirm(order);
        return order;
    }

    @Override
    public void undo() {
        if (snapshotBefore != null && order != null) {
            order.setStatus(snapshotBefore.getStatus());
        }
    }

    private static Order cloneStatus(Order o) {
        Order s = new Order();
        s.setStatus(o.getStatus());
        return s;
    }
}
