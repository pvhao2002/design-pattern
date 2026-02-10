package com.store.bridge;

import com.store.entity.Order;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Bridge — abstraction phía client, dùng NotificationSender (implementation).
 */
@Component
public class OrderNotification {

    private final NotificationSender sender;

    public OrderNotification(NotificationSender sender) {
        this.sender = sender;
    }

    public void notifyOrderConfirmed(Order order) {
        sender.sendOrderNotification(order, "Order confirmed: " + order.getCode());
    }
}
