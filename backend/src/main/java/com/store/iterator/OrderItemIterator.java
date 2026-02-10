package com.store.iterator;

import com.store.entity.OrderItem;

import java.util.Iterator;
import java.util.List;

/**
 * Design pattern: Iterator — duyệt OrderItem ẩn cấu trúc (có thể mở rộng filter/lazy).
 */
public class OrderItemIterator implements Iterator<OrderItem> {

    private final Iterator<OrderItem> delegate;

    public OrderItemIterator(List<OrderItem> items) {
        this.delegate = items != null ? items.iterator() : List.<OrderItem>of().iterator();
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public OrderItem next() {
        return delegate.next();
    }
}
