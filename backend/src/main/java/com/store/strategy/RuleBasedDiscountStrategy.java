package com.store.strategy;

import com.store.entity.Order;
import com.store.interpreter.DiscountRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Design pattern: Strategy — tính giảm giá dựa trên danh sách rule (Interpreter).
 */
@Component
public class RuleBasedDiscountStrategy implements DiscountStrategy {

    private final List<DiscountRule> rules;

    public RuleBasedDiscountStrategy(List<DiscountRule> rules) {
        this.rules = rules;
    }

    @Override
    public BigDecimal calculate(Order order) {
        if (rules == null || rules.isEmpty()) return BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        for (DiscountRule rule : rules) {
            BigDecimal d = rule.apply(order);
            totalDiscount = totalDiscount.add(d != null ? d : BigDecimal.ZERO);
        }
        return totalDiscount;
    }
}
