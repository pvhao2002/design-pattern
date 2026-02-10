package com.store.adapter;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design pattern: Adapter — chuyển ExternalPaymentService sang PaymentGateway.
 */
@Component
public class ExternalPaymentAdapter implements PaymentGateway {

    private final ExternalPaymentService externalService = new ExternalPaymentService();

    @Override
    public boolean charge(String reference, BigDecimal amount) {
        if (amount == null) return false;
        int code = externalService.processPayment(reference, amount.doubleValue());
        return code == 1;
    }

    @Override
    public boolean refund(String reference) {
        return externalService.rollbackPayment(reference) == 1;
    }
}
