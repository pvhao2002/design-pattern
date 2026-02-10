package com.store.statistics;

import com.store.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Design pattern: Strategy — thống kê doanh thu theo từng ngày.
 */
@Component
public class RevenueByDayStrategy implements RevenueReportStrategy {

    private final OrderRepository orderRepository;

    public RevenueByDayStrategy(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<RevenuePeriodResult> calculate(Instant from, Instant to) {
        List<RevenuePeriodResult> results = new ArrayList<>();
        LocalDate start = from.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = to.atZone(ZoneId.systemDefault()).toLocalDate();
        long days = ChronoUnit.DAYS.between(start, end) + 1;

        for (long i = 0; i < days; i++) {
            LocalDate day = start.plusDays(i);
            Instant dayStart = day.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant dayEnd = day.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            BigDecimal revenue = orderRepository.sumDeliveredRevenueBetween(dayStart, dayEnd);
            results.add(RevenuePeriodResult.builder()
                    .periodStart(dayStart)
                    .periodEnd(dayEnd)
                    .label(day.toString())
                    .revenue(revenue != null ? revenue : BigDecimal.ZERO)
                    .orderCount(null)
                    .build());
        }
        return results;
    }
}
