package com.store.statistics;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Design pattern: Facade + Strategy — API đơn giản cho thống kê doanh thu, chọn strategy theo loại báo cáo.
 */
@Service
public class RevenueStatisticsService {

    private final RevenueByDayStrategy byDayStrategy;
    private final RevenueByMonthStrategy byMonthStrategy;

    public RevenueStatisticsService(RevenueByDayStrategy byDayStrategy,
                                    RevenueByMonthStrategy byMonthStrategy) {
        this.byDayStrategy = byDayStrategy;
        this.byMonthStrategy = byMonthStrategy;
    }

    public List<RevenuePeriodResult> byDay(Instant from, Instant to) {
        return byDayStrategy.calculate(from, to);
    }

    public List<RevenuePeriodResult> byMonth(Instant from, Instant to) {
        return byMonthStrategy.calculate(from, to);
    }
}
