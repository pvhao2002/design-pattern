package com.store.visitor;

import com.store.entity.Order;
import com.store.entity.OrderItem;

/**
 * Design pattern: Visitor — thao tác trên Order/OrderItem không sửa entity.
 */
public interface OrderVisitor {

    void visit(Order order);

    void visit(OrderItem item);
}
