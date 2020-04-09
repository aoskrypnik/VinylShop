package com.vinyl.service;

import com.vinyl.dto.StatisticsDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface StatisticsService {

	Map<String, Integer> getAllStatistics(Timestamp from, Timestamp to);

	Map<Integer, Map<String, Integer>> getAllSalesmanStatistics(Timestamp from, Timestamp to);

	StatisticsDto getIncomeByPeriod(Timestamp from, Timestamp to);

	List<StatisticsDto> getSalesmanIncomeByPeriod(Timestamp from, Timestamp to);

	StatisticsDto getAvgIncomeByPeriod(Timestamp from, Timestamp to);

	List<StatisticsDto> getSalesmanAvgIncomeByPeriod(Timestamp from, Timestamp to);

	StatisticsDto getChecksNumByPeriod(Timestamp from, Timestamp to);

	List<StatisticsDto> getSalesmanChecksNumByPeriod(Timestamp from, Timestamp to);

	StatisticsDto getProceedsPeriod(Timestamp from, Timestamp to);

	List<StatisticsDto> getSalesmanProceedsByPeriod(Timestamp from, Timestamp to);

	Map<Integer, Map<String, Integer>> getStatisticsWithRecursiveByYear(String year);
}
