package com.store.factory;

import com.store.chain.MinAmountValidationHandler;
import com.store.chain.OrderValidationHandler;
import com.store.chain.StockValidationHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Abstract Factory — tạo họ các processor cho order (validator chain).
 */
@Component
public class OrderProcessorFactory {

    private final StockValidationHandler stockValidationHandler;
    private final MinAmountValidationHandler minAmountValidationHandler;

    private OrderValidationHandler chainHead;

    public OrderProcessorFactory(StockValidationHandler stockValidationHandler,
                                 MinAmountValidationHandler minAmountValidationHandler) {
        this.stockValidationHandler = stockValidationHandler;
        this.minAmountValidationHandler = minAmountValidationHandler;
    }

    @PostConstruct
    void initChain() {
        stockValidationHandler.setNext(minAmountValidationHandler);
        this.chainHead = stockValidationHandler;
    }

    /**
     * Trả về chain validation: stock → min amount.
     */
    public OrderValidationHandler createValidationChain() {
        return chainHead;
    }
}
