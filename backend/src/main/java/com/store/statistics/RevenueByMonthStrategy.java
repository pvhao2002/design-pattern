package com.store.statistics;

import com.store.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Design pattern: Strategy — thống kê doanh thu theo tháng.
 */
@Component
public class RevenueByMonthStrategy implements RevenueReportStrategy {

    private final OrderRepository orderRepository;

    public RevenueByMonthStrategy(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<RevenuePeriodResult> calculate(Instant from, Instant to) {
        List<RevenuePeriodResult> results = new ArrayList<>();
        YearMonth start = YearMonth.from(from.atZone(ZoneId.systemDefault()));
        YearMonth end = YearMonth.from(to.atZone(ZoneId.systemDefault()));

        for (YearMonth m = start; !m.isAfter(end); m = m.plusMonths(1)) {
            Instant periodStart = m.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant periodEnd = m.plusMonths(1).atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            BigDecimal revenue = orderRepository.sumDeliveredRevenueBetween(periodStart, periodEnd);
            results.add(RevenuePeriodResult.builder()
                    .periodStart(periodStart)
                    .periodEnd(periodEnd)
                    .label(m.toString())
                    .revenue(revenue != null ? revenue : BigDecimal.ZERO)
                    .orderCount(null)
                    .build());
        }
        return results;
    }
}
