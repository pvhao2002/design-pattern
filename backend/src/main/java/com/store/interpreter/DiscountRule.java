package com.store.interpreter;

import com.store.entity.Order;

import java.math.BigDecimal;

/**
 * Design pattern: Interpreter — diễn giải rule giảm giá (expression).
 */
public interface DiscountRule {

    /**
     * Áp dụng rule, trả về số tiền giảm (>= 0).
     */
    BigDecimal apply(Order order);
}
