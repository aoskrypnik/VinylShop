package com.vinyl.service.impl;

import com.vinyl.dao.StatisticsDao;
import com.vinyl.dto.StatisticsDto;
import com.vinyl.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Resource
	private StatisticsDao statisticsDao;

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
