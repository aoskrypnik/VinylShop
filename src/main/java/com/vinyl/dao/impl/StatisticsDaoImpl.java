package com.vinyl.dao.impl;

import com.vinyl.dao.StatisticsDao;
import com.vinyl.dto.StatisticsDto;
import com.vinyl.dto.StatisticsWithRecursiveDto;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	@Value("${sql.statistics.with.recursive.query.path}")
	private String statisticsWithRecursiveQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private RowMapper<StatisticsDto> statisticsRowMapper;
	@Resource
	private RowMapper<StatisticsWithRecursiveDto> statisticsWithRecursiveDtoRowMapper;

	@Transactional
	@Override
	public StatisticsDto getIncomeByPeriod(Timestamp from, Timestamp to) {
		return getStatisticsDto(from, to, incomeByPeriodQueryPath);
	}


	@Transactional
	@Override
	public List<StatisticsDto> getSalesmanIncomeByPeriod(Timestamp from, Timestamp to) {
		String salesmanIncomeByPeriodQuery = QuerySupplier.getQuery(salesmanIncomeByPeriodQueryPath);
		return jdbcTemplate.query(salesmanIncomeByPeriodQuery, new Object[]{from, to}, statisticsRowMapper);
	}

	@Transactional
	@Override
	public StatisticsDto getAvgIncomeByPeriod(Timestamp from, Timestamp to) {
		return getStatisticsDto(from, to, averageIncomeByPeriodQueryPath);
	}

	@Transactional
	@Override
	public List<StatisticsDto> getSalesmanAvgIncomeByPeriod(Timestamp from, Timestamp to) {
		String salesmanAverageIncomeByPeriodQuery = QuerySupplier.getQuery(salesmanAverageIncomeByPeriodQueryPath);
		return jdbcTemplate.query(salesmanAverageIncomeByPeriodQuery, new Object[]{from, to}, statisticsRowMapper);
	}

	@Transactional
	@Override
	public StatisticsDto getChecksNumByPeriod(Timestamp from, Timestamp to) {
		return getStatisticsDto(from, to, numberOfChecksByPeriodQueryPath);
	}

	@Transactional
	@Override
	public List<StatisticsDto> getSalesmanChecksNumByPeriod(Timestamp from, Timestamp to) {
		String numberOfSalesmanChecksQuery = QuerySupplier.getQuery(numberOfSalesmanChecksQueryPath);
		return jdbcTemplate.query(numberOfSalesmanChecksQuery, new Object[]{from, to}, statisticsRowMapper);
	}

	@Transactional
	@Override
	public StatisticsDto getProceedsPeriod(Timestamp from, Timestamp to) {
		return getStatisticsDto(from, to, proceedsByPeriodQueryPath);
	}

	@Transactional
	@Override
	public List<StatisticsDto> getSalesmanProceedsByPeriod(Timestamp from, Timestamp to) {
		String salesmanProceedsByPeriodQuery = QuerySupplier.getQuery(salesmanProceedsByPeriodQueryPath);
		return jdbcTemplate.query(salesmanProceedsByPeriodQuery, new Object[]{from, to}, statisticsRowMapper);
	}

	@Override
	public List<StatisticsWithRecursiveDto> getStatisticsWithRecursiveByYear(String year) {
		String statisticsWithRecursiveQuery = QuerySupplier.getQuery(statisticsWithRecursiveQueryPath);
		return jdbcTemplate.query(statisticsWithRecursiveQuery,
				new Object[]{year}, statisticsWithRecursiveDtoRowMapper);
	}

	private StatisticsDto getStatisticsDto(Timestamp from, Timestamp to, String queryPath) {
		String query = QuerySupplier.getQuery(queryPath);
		StatisticsDto statisticsDto = new StatisticsDto();
		List<Integer> res = jdbcTemplate.queryForList(query, new Object[]{from, to}, Integer.class);
		statisticsDto.setStatisticsResult(res.size() == 0 ? null : res.get(0));
		return statisticsDto;
	}
}
