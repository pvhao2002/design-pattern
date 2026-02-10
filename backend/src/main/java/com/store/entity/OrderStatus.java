package com.store.entity;

/**
 * Trạng thái đơn hàng (dùng trong State pattern).
 */
public enum OrderStatus {
    DRAFT,
    CONFIRMED,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
