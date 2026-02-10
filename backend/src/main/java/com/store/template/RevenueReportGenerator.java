package com.store.template;

import com.store.statistics.RevenuePeriodResult;
import com.store.statistics.RevenueStatisticsService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Design pattern: Template Method — báo cáo doanh thu (query từ service, format mặc định).
 */
@Component
public class RevenueReportGenerator extends AbstractReportGenerator {

    private final RevenueStatisticsService revenueStatisticsService;

    public RevenueReportGenerator(RevenueStatisticsService revenueStatisticsService) {
        this.revenueStatisticsService = revenueStatisticsService;
    }

    @Override
    protected List<RevenuePeriodResult> queryData(Instant from, Instant to) {
        return revenueStatisticsService.byDay(from, to);
    }
}
