package com.store.strategy;

import com.store.entity.Order;

import java.math.BigDecimal;

/**
 * Design pattern: Strategy — thuật toán tính giảm giá thay đổi được.
 */
public interface DiscountStrategy {

    BigDecimal calculate(Order order);
}
