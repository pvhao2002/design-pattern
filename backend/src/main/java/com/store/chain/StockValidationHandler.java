package com.store.chain;

import com.store.entity.Order;
import com.store.entity.OrderItem;
import com.store.entity.Product;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Chain of Responsibility — kiểm tra tồn kho.
 */
@Component
public class StockValidationHandler extends OrderValidationHandler {

    @Override
    protected boolean doValidate(Order order) {
        if (order.getItems() == null) return true;
        for (OrderItem item : order.getItems()) {
            Product p = item.getProduct();
            if (p != null && p.getStock() != null && item.getQuantity() != null
                    && p.getStock() < item.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for product: " + p.getName());
            }
        }
        return true;
    }
}
