package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private String code;
    private Long customerId;
    private String customerName;
    private String status;
    private List<OrderItemDTO> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private Instant createdAt;
    private Instant updatedAt;
}
