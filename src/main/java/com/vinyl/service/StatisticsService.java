package com.vinyl.service;

import com.vinyl.dto.StatisticsAvgIncomeDto;
import com.vinyl.dto.StatisticsChecksDto;
import com.vinyl.dto.StatisticsIncomeDto;
import com.vinyl.dto.StatisticsProceedsDto;
import com.vinyl.dto.StatisticsSalesmanAvgIncomeDto;
import com.vinyl.dto.StatisticsSalesmanChecksDto;
import com.vinyl.dto.StatisticsSalesmanIncomeDto;
import com.vinyl.dto.StatisticsSalesmanProceedsDto;

import java.sql.Timestamp;
import java.util.List;

public interface StatisticsService {

	StatisticsIncomeDto getIncomeByPeriod(Timestamp from, Timestamp to);

	List<StatisticsSalesmanIncomeDto> getSalesmanIncomeByPeriod(Timestamp from, Timestamp to);

	StatisticsAvgIncomeDto getAvgIncomeByPeriod(Timestamp from, Timestamp to);

	List<StatisticsSalesmanAvgIncomeDto> getSalesmanAvgIncomeByPeriod(Timestamp from, Timestamp to);

	StatisticsChecksDto getChecksNumByPeriod(Timestamp from, Timestamp to);

	List<StatisticsSalesmanChecksDto> getSalesmanChecksNumByPeriod(Timestamp from, Timestamp to);

	StatisticsProceedsDto getProceedsPeriod(Timestamp from, Timestamp to);

	List<StatisticsSalesmanProceedsDto> getSalesmanProceedsByPeriod(Timestamp from, Timestamp to);
}
