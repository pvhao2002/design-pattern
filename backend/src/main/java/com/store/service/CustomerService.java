package com.store.service;

import com.store.dto.CustomerDTO;
import com.store.dto.CustomerRequest;
import com.store.entity.Customer;
import com.store.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Facade: API đơn giản cho quản lý khách hàng.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {
        return customerRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
    }

    @Transactional
    public CustomerDTO create(CustomerRequest req) {
        Customer entity = Customer.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .address(req.getAddress())
                .build();
        return toDTO(customerRepository.save(entity));
    }

    @Transactional
    public CustomerDTO update(Long id, CustomerRequest req) {
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
        entity.setName(req.getName());
        entity.setEmail(req.getEmail());
        entity.setPhone(req.getPhone());
        entity.setAddress(req.getAddress());
        return toDTO(customerRepository.save(entity));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id))
            throw new IllegalArgumentException("Customer not found: " + id);
        customerRepository.deleteById(id);
    }

    private CustomerDTO toDTO(Customer c) {
        return CustomerDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .email(c.getEmail())
                .phone(c.getPhone())
                .address(c.getAddress())
                .build();
    }
}
