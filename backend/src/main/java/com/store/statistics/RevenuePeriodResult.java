package com.store.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenuePeriodResult {

    private Instant periodStart;
    private Instant periodEnd;
    private String label;
    private BigDecimal revenue;
    private Long orderCount;
}
