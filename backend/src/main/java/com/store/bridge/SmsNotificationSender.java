package com.store.bridge;

import com.store.entity.Order;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Bridge — implementation: gửi SMS.
 */
@Component
public class SmsNotificationSender implements NotificationSender {

    @Override
    public void sendOrderNotification(Order order, String message) {
        // Giả lập gửi SMS (theo customer phone)
    }
}
