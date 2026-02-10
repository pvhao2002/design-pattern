package com.store.decorator;

import com.store.dto.OrderDTO;
import com.store.dto.OrderRequest;

import java.util.List;

/**
 * Interface cho OrderService (để Decorator implement cùng contract).
 */
public interface OrderServiceDelegate {

    List<OrderDTO> findAll();
    List<OrderDTO> findByCustomerId(Long customerId);
    OrderDTO findById(Long id);
    OrderDTO create(OrderRequest request);
    OrderDTO update(Long id, OrderRequest request);
    void deleteById(Long id);
    OrderDTO confirm(Long id);
    OrderDTO pay(Long id);
    OrderDTO ship(Long id);
    OrderDTO deliver(Long id);
    OrderDTO cancel(Long id);
}
