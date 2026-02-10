package com.store.adapter;

import java.math.BigDecimal;

/**
 * Giả lập API ngoài (Adaptee) — interface khác với PaymentGateway.
 */
public class ExternalPaymentService {

    public int processPayment(String orderRef, double amountInVnd) {
        return amountInVnd > 0 ? 1 : -1; // 1 = success
    }

    public int rollbackPayment(String orderRef) {
        return 1;
    }
}
