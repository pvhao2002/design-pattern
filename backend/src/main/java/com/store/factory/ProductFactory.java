package com.store.factory;

import com.store.entity.Category;
import com.store.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design pattern: Factory — tạo Product.
 */
@Component
public class ProductFactory {

    public Product create(String name, BigDecimal price, Category category) {
        return create(name, null, price, null, 0, category);
    }

    public Product create(String name, String description, BigDecimal price, String sku,
                         int stock, Category category) {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price != null ? price : BigDecimal.ZERO)
                .sku(sku)
                .stock(stock)
                .category(category)
                .build();
    }
}
