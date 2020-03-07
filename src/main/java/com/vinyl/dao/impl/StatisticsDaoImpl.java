package com.vinyl.dao.impl;

import com.vinyl.dao.StatisticsDao;
import com.vinyl.dto.StatisticsAvgIncomeDto;
import com.vinyl.dto.StatisticsChecksDto;
import com.vinyl.dto.StatisticsIncomeDto;
import com.vinyl.dto.StatisticsProceedsDto;
import com.vinyl.dto.StatisticsSalesmanAvgIncomeDto;
import com.vinyl.dto.StatisticsSalesmanChecksDto;
import com.vinyl.dto.StatisticsSalesmanIncomeDto;
import com.vinyl.dto.StatisticsSalesmanProceedsDto;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class StatisticsDaoImpl implements StatisticsDao {

	@Value("${sql.statistics.income.by.period.query.path}")
	private String incomeByPeriodQueryPath;
	@Value("${sql.statistics.average.income.by.period.query.path}")
	private String averageIncomeByPeriodQueryPath;
	@Value("${sql.statistics.number.of.checks.by.period.query.path}")
	private String numberOfChecksByPeriodQueryPath;
	@Value("${sql.statistics.proceeds.by.period.query.path}")
	private String proceedsByPeriodQueryPath;
	@Value("${sql.statistics.salesman.income.by.period.query.path}")
	private String salesmanIncomeByPeriodQueryPath;
	@Value("${sql.statistics.salesman.average.income.by.period.query.path}")
	private String salesmanAverageIncomeByPeriodQueryPath;
	@Value("${sql.statistics.salesman.number.of.checks.by.period.query.path}")
	private String numberOfSalesmanChecksQueryPath;
	@Value("${sql.statistics.salesman.proceeds.by.period.query.path}")
	private String salesmanProceedsByPeriodQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<StatisticsSalesmanIncomeDto> statisticsSalesmanIncomeDtoRowMapper;
	@Resource
	private RowMapper<StatisticsSalesmanAvgIncomeDto> statisticsSalesmanAvgIncomeDtoRowMapper;
	@Resource
	private RowMapper<StatisticsSalesmanChecksDto> statisticsSalesmanChecksDtoRowMapper;
	@Resource
	private RowMapper<StatisticsSalesmanProceedsDto> statisticsSalesmanProceedsDtoRowMapper;

	@Override
	public StatisticsIncomeDto getIncomeByPeriod(Timestamp from, Timestamp to) {
		String incomeByPeriodQuery = QuerySupplier.getQuery(incomeByPeriodQueryPath);
		StatisticsIncomeDto statisticsIncomeDto = new StatisticsIncomeDto();
		List<Integer> income = jdbcTemplate.queryForList(incomeByPeriodQuery, new Object[]{from, to}, Integer.class);
		statisticsIncomeDto.setIncome(income.size() == 0 ? null : income.get(0));
		return statisticsIncomeDto;
	}

	@Override
	public List<StatisticsSalesmanIncomeDto> getSalesmanIncomeByPeriod(Timestamp from, Timestamp to) {
		String salesmanIncomeByPeriodQuery = QuerySupplier.getQuery(salesmanIncomeByPeriodQueryPath);
		return jdbcTemplate.query(salesmanIncomeByPeriodQuery, new Object[]{from, to}, statisticsSalesmanIncomeDtoRowMapper);
	}

	@Override
	public StatisticsAvgIncomeDto getAvgIncomeByPeriod(Timestamp from, Timestamp to) {
		String averageIncomeByPeriodQuery = QuerySupplier.getQuery(averageIncomeByPeriodQueryPath);
		StatisticsAvgIncomeDto statisticsAvgIncomeDto = new StatisticsAvgIncomeDto();
		List<Double> income = jdbcTemplate.queryForList(averageIncomeByPeriodQuery, new Object[]{from, to}, Double.class);
		statisticsAvgIncomeDto.setAverageIncome(income.size() == 0 ? null : income.get(0));
		return statisticsAvgIncomeDto;
	}

	@Override
	public List<StatisticsSalesmanAvgIncomeDto> getSalesmanAvgIncomeByPeriod(Timestamp from, Timestamp to) {
		String salesmanAverageIncomeByPeriodQuery = QuerySupplier.getQuery(salesmanAverageIncomeByPeriodQueryPath);
		return jdbcTemplate.query(salesmanAverageIncomeByPeriodQuery, new Object[]{from, to}, statisticsSalesmanAvgIncomeDtoRowMapper);
	}

	@Override
	public StatisticsChecksDto getChecksNumByPeriod(Timestamp from, Timestamp to) {
		String numberOfChecksByPeriodQuery = QuerySupplier.getQuery(numberOfChecksByPeriodQueryPath);
		StatisticsChecksDto statisticsChecksDto = new StatisticsChecksDto();
		List<Integer> checksNum = jdbcTemplate.queryForList(numberOfChecksByPeriodQuery, new Object[]{from, to}, Integer.class);
		statisticsChecksDto.setNumberOfChecks(checksNum.size() == 0 ? null : checksNum.get(0));
		return statisticsChecksDto;
	}

	@Override
	public List<StatisticsSalesmanChecksDto> getSalesmanChecksNumByPeriod(Timestamp from, Timestamp to) {
		String numberOfSalesmanChecksQuery = QuerySupplier.getQuery(numberOfSalesmanChecksQueryPath);
		return jdbcTemplate.query(numberOfSalesmanChecksQuery, new Object[]{from, to}, statisticsSalesmanChecksDtoRowMapper);
	}

	@Override
	public StatisticsProceedsDto getProceedsPeriod(Timestamp from, Timestamp to) {
		String proceedsByPeriodQuery = QuerySupplier.getQuery(proceedsByPeriodQueryPath);
		StatisticsProceedsDto statisticsProceedsDto = new StatisticsProceedsDto();
		List<Integer> checksNum = jdbcTemplate.queryForList(proceedsByPeriodQuery, new Object[]{from, to}, Integer.class);
		statisticsProceedsDto.setProceeds(checksNum.size() == 0 ? null : checksNum.get(0));
		return statisticsProceedsDto;
	}

	@Override
	public List<StatisticsSalesmanProceedsDto> getSalesmanProceedsByPeriod(Timestamp from, Timestamp to) {
		String salesmanProceedsByPeriodQuery = QuerySupplier.getQuery(salesmanProceedsByPeriodQueryPath);
		return jdbcTemplate.query(salesmanProceedsByPeriodQuery, new Object[]{from, to}, statisticsSalesmanProceedsDtoRowMapper);
	}
}
