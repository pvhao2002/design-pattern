package com.store.config;

import com.store.interpreter.DiscountRule;
import com.store.interpreter.PercentIfTotalAboveRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Design pattern: Singleton — cấu hình / cache manager một instance (Spring @Configuration singleton).
 */
@Configuration
public class StoreConfig {

    @Bean
    public ConcurrentHashMap<String, Object> appCache() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public DiscountRule percentDiscountRule() {
        return new PercentIfTotalAboveRule(new BigDecimal("1000000"), new BigDecimal("10"));
    }
}
