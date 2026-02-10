package com.store.chain;

import com.store.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design pattern: Chain of Responsibility — kiểm tra tổng đơn tối thiểu.
 */
@Component
public class MinAmountValidationHandler extends OrderValidationHandler {

    @Value("${store.order.min-amount:0}")
    private BigDecimal minAmount;

    @Override
    protected boolean doValidate(Order order) {
        if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) <= 0) return true;
        BigDecimal total = order.getTotalAmount() != null ? order.getTotalAmount() : order.calculateTotal();
        if (total == null || total.compareTo(minAmount) < 0) {
            throw new IllegalStateException("Order total must be at least " + minAmount);
        }
        return true;
    }
}
