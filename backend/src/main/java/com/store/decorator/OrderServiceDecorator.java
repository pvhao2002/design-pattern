package com.store.decorator;

import com.store.dto.OrderDTO;
import com.store.dto.OrderRequest;
import com.store.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Design pattern: Decorator — bọc OrderService, thêm hành vi (log, cache) không sửa OrderService gốc.
 */
@Service
public class OrderServiceDecorator implements OrderServiceDelegate {

    private final OrderService delegate;

    public OrderServiceDecorator(OrderService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<OrderDTO> findAll() {
        return delegate.findAll();
    }

    @Override
    public List<OrderDTO> findByCustomerId(Long customerId) {
        return delegate.findByCustomerId(customerId);
    }

    @Override
    public OrderDTO findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public OrderDTO create(OrderRequest request) {
        return delegate.create(request);
    }

    @Override
    public OrderDTO update(Long id, OrderRequest request) {
        return delegate.update(id, request);
    }

    @Override
    public void deleteById(Long id) {
        delegate.deleteById(id);
    }

    @Override
    public OrderDTO confirm(Long id) {
        return delegate.confirm(id);
    }

    @Override
    public OrderDTO pay(Long id) {
        return delegate.pay(id);
    }

    @Override
    public OrderDTO ship(Long id) {
        return delegate.ship(id);
    }

    @Override
    public OrderDTO deliver(Long id) {
        return delegate.deliver(id);
    }

    @Override
    public OrderDTO cancel(Long id) {
        return delegate.cancel(id);
    }
}
