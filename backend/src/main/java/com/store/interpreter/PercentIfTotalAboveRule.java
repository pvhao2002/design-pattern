package com.store.interpreter;

import com.store.entity.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Design pattern: Interpreter — rule "giảm X% nếu tổng đơn >= Y".
 */
public class PercentIfTotalAboveRule implements DiscountRule {

    private final BigDecimal threshold;
    private final BigDecimal percent;

    public PercentIfTotalAboveRule(BigDecimal threshold, BigDecimal percent) {
        this.threshold = threshold;
        this.percent = percent;
    }

    @Override
    public BigDecimal apply(Order order) {
        if (order == null) return BigDecimal.ZERO;
        BigDecimal total = order.getTotalAmount() != null ? order.getTotalAmount() : order.calculateTotal();
        if (total == null || threshold == null || percent == null) return BigDecimal.ZERO;
        if (total.compareTo(threshold) < 0) return BigDecimal.ZERO;
        return total.multiply(percent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
