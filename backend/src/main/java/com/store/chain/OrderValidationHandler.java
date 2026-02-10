package com.store.chain;

import com.store.entity.Order;

/**
 * Design pattern: Chain of Responsibility — handler base cho validation đơn hàng.
 */
public abstract class OrderValidationHandler {

    private OrderValidationHandler next;

    public void setNext(OrderValidationHandler next) {
        this.next = next;
    }

    public final boolean validate(Order order) {
        if (!doValidate(order)) {
            return false;
        }
        return next == null || next.validate(order);
    }

    protected abstract boolean doValidate(Order order);
}
