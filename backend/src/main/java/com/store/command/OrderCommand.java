package com.store.command;

import com.store.entity.Order;

/**
 * Design pattern: Command — đóng gói thao tác trên đơn hàng.
 */
public interface OrderCommand {

    Order execute();

    void undo();
}
