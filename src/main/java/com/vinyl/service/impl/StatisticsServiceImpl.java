package com.vinyl.service.impl;

import com.vinyl.dao.StatisticsDao;
import com.vinyl.dto.StatisticsDto;
import com.vinyl.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Resource
	private StatisticsDao statisticsDao;

	@Override
	public Map<String, Integer> getAllStatistics(Timestamp from, Timestamp to) {
		Map<String, Integer> statisticsMap = new HashMap<>();
		StatisticsDto income = getIncomeByPeriod(from, to);
		StatisticsDto avgIncome = getAvgIncomeByPeriod(from, to);
		StatisticsDto checksNum = getChecksNumByPeriod(from, to);
		StatisticsDto proceeds = getProceedsPeriod(from, to);

		statisticsMap.put("income", income.getStatisticsResult());
		statisticsMap.put("avgIncome", avgIncome.getStatisticsResult());
		statisticsMap.put("checksNum", checksNum.getStatisticsResult());
		statisticsMap.put("proceeds", proceeds.getStatisticsResult());


		return statisticsMap;
	}

	@Override
	public Map<Integer, Map<String, Integer>> getAllSalesmanStatistics(Timestamp from, Timestamp to) {
		Map<Integer, Map<String, Integer>> statisticsMap = new HashMap<>();
		Map<String, Integer> statisticsData = new HashMap<>();
		List<StatisticsDto> income = getSalesmanIncomeByPeriod(from, to);
		List<StatisticsDto> avgIncome = getSalesmanAvgIncomeByPeriod(from, to);
		List<StatisticsDto> checksNum = getSalesmanChecksNumByPeriod(from, to);
		List<StatisticsDto> proceeds = getSalesmanProceedsByPeriod(from, to);

		fillStatisticsMap(statisticsMap, statisticsData, income, "salesmanIncome");

		fillStatisticsMap(statisticsMap, statisticsData, avgIncome, "salesmanAvgIncome");

		fillStatisticsMap(statisticsMap, statisticsData, checksNum, "salesmanChecksNum");

		fillStatisticsMap(statisticsMap, statisticsData, proceeds, "salesmanProceeds");

		return statisticsMap;
	}

	private void fillStatisticsMap(Map<Integer, Map<String, Integer>> statisticsMap, Map<String, Integer> statistisData, List<StatisticsDto> statisticsDtoList, String salesmanIncome) {
		statisticsDtoList.forEach(statisticsDto -> {
			statistisData.put(salesmanIncome, statisticsDto.getStatisticsResult());
			statisticsMap.put(statisticsDto.getSalesmanTabNum(), statistisData);
		});
	}

	@Override
	public StatisticsDto getIncomeByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getIncomeByPeriod(from, to);
	}

	@Override
	public List<StatisticsDto> getSalesmanIncomeByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getSalesmanIncomeByPeriod(from, to);
	}

	@Override
	public StatisticsDto getAvgIncomeByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getAvgIncomeByPeriod(from, to);
	}

	@Override
	public List<StatisticsDto> getSalesmanAvgIncomeByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getSalesmanAvgIncomeByPeriod(from, to);
	}

	@Override
	public StatisticsDto getChecksNumByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getChecksNumByPeriod(from, to);
	}

	@Override
	public List<StatisticsDto> getSalesmanChecksNumByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getSalesmanChecksNumByPeriod(from, to);
	}

	@Override
	public StatisticsDto getProceedsPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getProceedsPeriod(from, to);
	}

	@Override
	public List<StatisticsDto> getSalesmanProceedsByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getSalesmanProceedsByPeriod(from, to);
	}
}
