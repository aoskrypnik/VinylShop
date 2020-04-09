package com.vinyl.dao;

import com.vinyl.dto.StatisticsDto;
import com.vinyl.dto.StatisticsWithRecursiveDto;

import java.sql.Timestamp;
import java.util.List;

public interface StatisticsDao {

	StatisticsDto getIncomeByPeriod(Timestamp from, Timestamp to);

	List<StatisticsDto> getSalesmanIncomeByPeriod(Timestamp from, Timestamp to);

	StatisticsDto getAvgIncomeByPeriod(Timestamp from, Timestamp to);

	List<StatisticsDto> getSalesmanAvgIncomeByPeriod(Timestamp from, Timestamp to);

	StatisticsDto getChecksNumByPeriod(Timestamp from, Timestamp to);

	List<StatisticsDto> getSalesmanChecksNumByPeriod(Timestamp from, Timestamp to);

	StatisticsDto getProceedsPeriod(Timestamp from, Timestamp to);

	List<StatisticsDto> getSalesmanProceedsByPeriod(Timestamp from, Timestamp to);

	List<StatisticsWithRecursiveDto> getStatisticsWithRecursiveByYear(String year);

}
