package com.store.adapter;

import java.math.BigDecimal;

/**
 * Interface thanh toán nội bộ (target).
 */
public interface PaymentGateway {

    boolean charge(String reference, BigDecimal amount);

    boolean refund(String reference);
}
