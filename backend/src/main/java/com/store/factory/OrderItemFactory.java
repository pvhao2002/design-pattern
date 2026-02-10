package com.store.factory;

import com.store.entity.OrderItem;
import com.store.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design pattern: Factory — tạo OrderItem.
 */
@Component
public class OrderItemFactory {

    public OrderItem create(Product product, int quantity) {
        BigDecimal unitPrice = product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO;
        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();
    }

    public OrderItem create(Product product, int quantity, BigDecimal unitPrice) {
        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .unitPrice(unitPrice != null ? unitPrice : product.getPrice())
                .build();
    }
}
