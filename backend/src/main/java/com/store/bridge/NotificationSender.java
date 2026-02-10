package com.store.bridge;

import com.store.entity.Order;

/**
 * Design pattern: Bridge — abstraction (Implementor gửi thông báo).
 */
public interface NotificationSender {

    void sendOrderNotification(Order order, String message);
}
