package com.store.controller;

import com.store.decorator.OrderServiceDelegate;
import com.store.dto.OrderDTO;
import com.store.dto.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceDelegate orderService;

    public OrderController(OrderServiceDelegate orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> findAll(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            return orderService.findByCustomerId(customerId);
        }
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PutMapping("/{id}")
    public OrderDTO update(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        return orderService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.deleteById(id);
    }

    @PostMapping("/{id}/confirm")
    public OrderDTO confirm(@PathVariable Long id) {
        return orderService.confirm(id);
    }

    @PostMapping("/{id}/pay")
    public OrderDTO pay(@PathVariable Long id) {
        return orderService.pay(id);
    }

    @PostMapping("/{id}/ship")
    public OrderDTO ship(@PathVariable Long id) {
        return orderService.ship(id);
    }

    @PostMapping("/{id}/deliver")
    public OrderDTO deliver(@PathVariable Long id) {
        return orderService.deliver(id);
    }

    @PostMapping("/{id}/cancel")
    public OrderDTO cancel(@PathVariable Long id) {
        return orderService.cancel(id);
    }
}
