package com.store.observer;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Observer — listener khi đơn đổi trạng thái (có thể gửi email, cập nhật thống kê).
 */
@Component
public class OrderStatusChangedListener {

    @EventListener
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        // Có thể: gửi email, cập nhật cache thống kê, log audit
        // Giữ xử lý đơn giản để minh họa pattern
    }
}
