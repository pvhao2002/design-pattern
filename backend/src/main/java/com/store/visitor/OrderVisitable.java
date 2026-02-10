package com.store.visitor;

import com.store.entity.Order;
import com.store.entity.OrderItem;

/**
 * Helper để entity có thể accept visitor (entity không sửa — dùng từ bên ngoài).
 */
public final class OrderVisitable {

    private OrderVisitable() {}

    public static void accept(Order order, OrderVisitor visitor) {
        if (order == null || visitor == null) return;
        visitor.visit(order);
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                visitor.visit(item);
            }
        }
    }
}
