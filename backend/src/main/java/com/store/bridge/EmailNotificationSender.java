package com.store.bridge;

import com.store.entity.Order;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Bridge — implementation: gửi qua email.
 */
@Primary
@Component
public class EmailNotificationSender implements NotificationSender {

    @Override
    public void sendOrderNotification(Order order, String message) {
        // Giả lập gửi email (theo customer email)
    }
}
