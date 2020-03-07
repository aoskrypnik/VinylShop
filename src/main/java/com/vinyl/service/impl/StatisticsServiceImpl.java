package com.vinyl.service.impl;

import com.vinyl.dao.StatisticsDao;
import com.vinyl.dto.StatisticsAvgIncomeDto;
import com.vinyl.dto.StatisticsChecksDto;
import com.vinyl.dto.StatisticsIncomeDto;
import com.vinyl.dto.StatisticsProceedsDto;
import com.vinyl.dto.StatisticsSalesmanAvgIncomeDto;
import com.vinyl.dto.StatisticsSalesmanChecksDto;
import com.vinyl.dto.StatisticsSalesmanIncomeDto;
import com.vinyl.dto.StatisticsSalesmanProceedsDto;
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
	public StatisticsIncomeDto getIncomeByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getIncomeByPeriod(from, to);
	}

	@Override
	public List<StatisticsSalesmanIncomeDto> getSalesmanIncomeByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getSalesmanIncomeByPeriod(from, to);
	}

	@Override
	public StatisticsAvgIncomeDto getAvgIncomeByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getAvgIncomeByPeriod(from, to);
	}

	@Override
	public List<StatisticsSalesmanAvgIncomeDto> getSalesmanAvgIncomeByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getSalesmanAvgIncomeByPeriod(from, to);
	}

	@Override
	public StatisticsChecksDto getChecksNumByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getChecksNumByPeriod(from, to);
	}

	@Override
	public List<StatisticsSalesmanChecksDto> getSalesmanChecksNumByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getSalesmanChecksNumByPeriod(from, to);
	}

	@Override
	public StatisticsProceedsDto getProceedsPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getProceedsPeriod(from, to);
	}

	@Override
	public List<StatisticsSalesmanProceedsDto> getSalesmanProceedsByPeriod(Timestamp from, Timestamp to) {
		return statisticsDao.getSalesmanProceedsByPeriod(from, to);
	}
}
