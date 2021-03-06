package com.vinyl.service.impl;

import com.vinyl.dao.StatisticsDao;
import com.vinyl.dto.StatisticsDto;
import com.vinyl.dto.StatisticsWithRecursiveDto;
import com.vinyl.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

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
	public Map<Integer, Map<String, Integer>> getStatisticsWithRecursiveByYear(String year) {
		List<StatisticsWithRecursiveDto> statisticsWithRecursiveDto = statisticsDao.getStatisticsWithRecursiveByYear(year);
		Map<Integer, Map<String, Integer>> statisticsMap = new HashMap<>();
		fillStatisticsWithRecursiveMap(statisticsMap, statisticsWithRecursiveDto, 1);
		return statisticsMap;
	}

	@Override
	public Map<Integer, Map<String, Integer>> getAllSalesmanStatistics(Timestamp from, Timestamp to) {
		Map<Integer, Map<String, Integer>> statisticsMap = new HashMap<>();
		List<StatisticsDto> income = getSalesmanIncomeByPeriod(from, to);
		List<StatisticsDto> avgIncome = getSalesmanAvgIncomeByPeriod(from, to);
		List<StatisticsDto> checksNum = getSalesmanChecksNumByPeriod(from, to);
		List<StatisticsDto> proceeds = getSalesmanProceedsByPeriod(from, to);

		fillStatisticsMap(statisticsMap, income, "salesmanIncome");

		fillStatisticsMap(statisticsMap, avgIncome, "salesmanAvgIncome");

		fillStatisticsMap(statisticsMap, checksNum, "salesmanChecksNum");

		fillStatisticsMap(statisticsMap, proceeds, "salesmanProceeds");

		return statisticsMap;
	}

	private void fillStatisticsMap(Map<Integer, Map<String, Integer>> statisticsMap,
								   List<StatisticsDto> statisticsDtoList,
								   String statisticsMetric) {
		statisticsDtoList.forEach(statisticsDto -> {
			Map<String, Integer> innerMap = statisticsMap.get(statisticsDto.getSalesmanTabNum());
			if (isNull(innerMap)) {
				Map<String, Integer> newInnerMap = new HashMap<>();
				newInnerMap.put(statisticsMetric, statisticsDto.getStatisticsResult());
				statisticsMap.put(statisticsDto.getSalesmanTabNum(), newInnerMap);
			} else {
				innerMap.put(statisticsMetric, statisticsDto.getStatisticsResult());
				statisticsMap.put(statisticsDto.getSalesmanTabNum(), innerMap);
			}
		});
	}

	private void fillStatisticsWithRecursiveMap(Map<Integer, Map<String, Integer>> statisticsMap,
												List<StatisticsWithRecursiveDto> statisticsWithRecursiveDtoList,
												Integer statisticsMonthId) {
		for (StatisticsWithRecursiveDto statisticsWithRecursiveDto : statisticsWithRecursiveDtoList) {
			Map<String, Integer> innerMap = statisticsMap.get(statisticsMonthId);
			if (isNull(innerMap)) {
				Map<String, Integer> newInnerMap = new HashMap<>();
				newInnerMap.put("checksNum", statisticsWithRecursiveDto.getCheckNum());
				newInnerMap.put("income", statisticsWithRecursiveDto.getIncome());
				newInnerMap.put("avgCheck", statisticsWithRecursiveDto.getAvgCheck());
				newInnerMap.put("proceeds", statisticsWithRecursiveDto.getProceeds());
				statisticsMap.put(statisticsMonthId, newInnerMap);
			} else {
				innerMap.put("checksNum", statisticsWithRecursiveDto.getCheckNum());
				innerMap.put("income", statisticsWithRecursiveDto.getIncome());
				innerMap.put("avgCheck", statisticsWithRecursiveDto.getAvgCheck());
				innerMap.put("proceeds", statisticsWithRecursiveDto.getProceeds());
				statisticsMap.put(statisticsMonthId, innerMap);
			}
			statisticsMonthId++;
		}
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
