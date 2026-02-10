package com.store.service;

import com.store.dto.*;
import com.store.entity.*;
import com.store.factory.OrderFactory;
import com.store.factory.OrderItemFactory;
import com.store.factory.OrderProcessorFactory;
import com.store.observer.OrderStatusChangedEvent;
import com.store.repository.CustomerRepository;
import com.store.repository.OrderRepository;
import com.store.repository.ProductRepository;
import com.store.state.OrderStateContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade: API đơn giản cho đơn hàng (repository + factory + state).
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderFactory orderFactory;
    private final OrderItemFactory orderItemFactory;
    private final OrderStateContext stateContext;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderProcessorFactory orderProcessorFactory;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
                        ProductRepository productRepository, OrderFactory orderFactory,
                        OrderItemFactory orderItemFactory, OrderStateContext stateContext,
                        ApplicationEventPublisher eventPublisher,
                        OrderProcessorFactory orderProcessorFactory) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderFactory = orderFactory;
        this.orderItemFactory = orderItemFactory;
        this.stateContext = stateContext;
        this.eventPublisher = eventPublisher;
        this.orderProcessorFactory = orderProcessorFactory;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        return orderRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    @Transactional
    public OrderDTO create(OrderRequest req) {
        Customer customer = req.getCustomerId() != null
                ? customerRepository.findById(req.getCustomerId()).orElse(null)
                : null;
        Order order = orderFactory.create(customer);
        for (OrderItemRequest ir : req.getItems()) {
            Product product = productRepository.findById(ir.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + ir.getProductId()));
            OrderItem item = orderItemFactory.create(product, ir.getQuantity());
            order.addItem(item);
        }
        order.setTotalAmount(order.calculateTotal());
        return toDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO update(Long id, OrderRequest req) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        if (order.getStatus() != OrderStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT orders can be updated");
        }
        if (req.getCustomerId() != null) {
            order.setCustomer(customerRepository.findById(req.getCustomerId()).orElse(null));
        }
        if (order.getItems() != null) {
            order.getItems().clear();
        }
        for (OrderItemRequest ir : req.getItems()) {
            Product product = productRepository.findById(ir.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + ir.getProductId()));
            OrderItem item = orderItemFactory.create(product, ir.getQuantity());
            order.addItem(item);
        }
        order.setTotalAmount(order.calculateTotal());
        return toDTO(orderRepository.save(order));
    }

    @Transactional
    public void deleteById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        if (order.getStatus() != OrderStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT orders can be deleted");
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public OrderDTO confirm(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        orderProcessorFactory.createValidationChain().validate(order);
        OrderStatus previous = order.getStatus();
        stateContext.getState(order).confirm(order);
        order = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderStatusChangedEvent(this, order, previous));
        return toDTO(order);
    }

    @Transactional
    public OrderDTO pay(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        OrderStatus previous = order.getStatus();
        stateContext.getState(order).pay(order);
        order = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderStatusChangedEvent(this, order, previous));
        return toDTO(order);
    }

    @Transactional
    public OrderDTO ship(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        OrderStatus previous = order.getStatus();
        stateContext.getState(order).ship(order);
        order = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderStatusChangedEvent(this, order, previous));
        return toDTO(order);
    }

    @Transactional
    public OrderDTO deliver(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        OrderStatus previous = order.getStatus();
        stateContext.getState(order).deliver(order);
        order = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderStatusChangedEvent(this, order, previous));
        return toDTO(order);
    }

    @Transactional
    public OrderDTO cancel(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        OrderStatus previous = order.getStatus();
        stateContext.getState(order).cancel(order);
        order = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderStatusChangedEvent(this, order, previous));
        return toDTO(order);
    }

    private OrderDTO toDTO(Order o) {
        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        if (o.getItems() != null) {
            for (OrderItem i : o.getItems()) {
                itemDTOs.add(OrderItemDTO.builder()
                        .id(i.getId())
                        .productId(i.getProduct() != null ? i.getProduct().getId() : null)
                        .productName(i.getProduct() != null ? i.getProduct().getName() : null)
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .subtotal(i.getSubtotal())
                        .build());
            }
        }
        return OrderDTO.builder()
                .id(o.getId())
                .code(o.getCode())
                .customerId(o.getCustomer() != null ? o.getCustomer().getId() : null)
                .customerName(o.getCustomer() != null ? o.getCustomer().getName() : null)
                .status(o.getStatus() != null ? o.getStatus().name() : null)
                .items(itemDTOs)
                .totalAmount(o.getTotalAmount())
                .createdAt(o.getCreatedAt())
                .updatedAt(o.getUpdatedAt())
                .build();
    }
}
