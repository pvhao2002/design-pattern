package com.store.state;

import com.store.entity.Order;

/**
 * Design pattern: State — interface trạng thái đơn hàng.
 */
public interface OrderState {

    void confirm(Order order);

    void pay(Order order);

    void ship(Order order);

    void deliver(Order order);

    void cancel(Order order);
}
