package com.store.visitor;

import com.store.entity.Order;
import com.store.entity.OrderItem;

import java.math.BigDecimal;

/**
 * Design pattern: Visitor — tính tổng tiền đơn từ items (duyệt cấu trúc).
 */
public class TotalAmountVisitor implements OrderVisitor {

    private BigDecimal total = BigDecimal.ZERO;

    @Override
    public void visit(Order order) {
        total = BigDecimal.ZERO;
    }

    @Override
    public void visit(OrderItem item) {
        if (item.getSubtotal() != null) {
            total = total.add(item.getSubtotal());
        }
    }

    public BigDecimal getTotal() {
        return total;
    }
}
