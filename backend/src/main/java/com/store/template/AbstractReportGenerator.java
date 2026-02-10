package com.store.template;

import com.store.statistics.RevenuePeriodResult;

import java.time.Instant;
import java.util.List;

/**
 * Design pattern: Template Method — skeleton báo cáo: query → aggregate → format.
 */
public abstract class AbstractReportGenerator {

    public final List<RevenuePeriodResult> generate(Instant from, Instant to) {
        List<RevenuePeriodResult> data = queryData(from, to);
        data = aggregate(data);
        return format(data);
    }

    protected abstract List<RevenuePeriodResult> queryData(Instant from, Instant to);

    protected List<RevenuePeriodResult> aggregate(List<RevenuePeriodResult> data) {
        return data;
    }

    protected List<RevenuePeriodResult> format(List<RevenuePeriodResult> data) {
        return data;
    }
}
