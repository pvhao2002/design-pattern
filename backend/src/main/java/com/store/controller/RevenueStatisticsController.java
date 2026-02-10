package com.store.controller;

import com.store.statistics.RevenuePeriodResult;
import com.store.statistics.RevenueStatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class RevenueStatisticsController {

    private final RevenueStatisticsService revenueStatisticsService;

    public RevenueStatisticsController(RevenueStatisticsService revenueStatisticsService) {
        this.revenueStatisticsService = revenueStatisticsService;
    }

    @GetMapping("/revenue")
    public List<RevenuePeriodResult> getRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "day") String groupBy) {
        Instant fromInstant = from.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant toInstant = to.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        if ("month".equalsIgnoreCase(groupBy)) {
            return revenueStatisticsService.byMonth(fromInstant, toInstant);
        }
        return revenueStatisticsService.byDay(fromInstant, toInstant);
    }
}
