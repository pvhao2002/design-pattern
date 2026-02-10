package com.store.proxy;

import com.store.decorator.OrderServiceDelegate;
import com.store.dto.OrderDTO;
import com.store.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Design pattern: Proxy — đại diện OrderService, thêm kiểm tra truy cập (hoặc cache) trước khi gọi.
 */
@Primary
@Service
public class OrderServiceProxy implements OrderServiceDelegate {

    private final OrderServiceDelegate target;

    public OrderServiceProxy(@Qualifier("orderServiceDecorator") OrderServiceDelegate target) {
        this.target = target;
    }

    @Override
    public List<OrderDTO> findAll() {
        return target.findAll();
    }

    @Override
    public List<OrderDTO> findByCustomerId(Long customerId) {
        return target.findByCustomerId(customerId);
    }

    @Override
    public OrderDTO findById(Long id) {
        return target.findById(id);
    }

    @Override
    public OrderDTO create(OrderRequest request) {
        return target.create(request);
    }

    @Override
    public OrderDTO update(Long id, OrderRequest request) {
        return target.update(id, request);
    }

    @Override
    public void deleteById(Long id) {
        target.deleteById(id);
    }

    @Override
    public OrderDTO confirm(Long id) {
        return target.confirm(id);
    }

    @Override
    public OrderDTO pay(Long id) {
        return target.pay(id);
    }

    @Override
    public OrderDTO ship(Long id) {
        return target.ship(id);
    }

    @Override
    public OrderDTO deliver(Long id) {
        return target.deliver(id);
    }

    @Override
    public OrderDTO cancel(Long id) {
        return target.cancel(id);
    }
}
