package com.store.statistics;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Design pattern: Strategy — cách tính/thống kê doanh thu (theo ngày/tuần/tháng).
 */
public interface RevenueReportStrategy {

    List<RevenuePeriodResult> calculate(Instant from, Instant to);
}
