package com.vinyl.controller;

import com.vinyl.dto.StatisticsAvgIncomeDto;
import com.vinyl.dto.StatisticsChecksDto;
import com.vinyl.dto.StatisticsIncomeDto;
import com.vinyl.dto.StatisticsProceedsDto;
import com.vinyl.dto.StatisticsSalesmanAvgIncomeDto;
import com.vinyl.dto.StatisticsSalesmanChecksDto;
import com.vinyl.dto.StatisticsSalesmanIncomeDto;
import com.vinyl.dto.StatisticsSalesmanProceedsDto;
import com.vinyl.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	@Resource
	private StatisticsService statisticsService;

	@GetMapping("/income")
	public ResponseEntity<?> getIncomesByPeriod(@RequestParam String from, @RequestParam String to) throws ParseException {
		StatisticsIncomeDto incomes = statisticsService.getIncomeByPeriod(dateConverter(from), dateConverter(to));
		if (isNull(incomes)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(incomes);
	}

	@GetMapping("/salesmen-income")
	public ResponseEntity<?> getSalesmanIncomesByPeriod(
			@RequestParam String from,
			@RequestParam String to) throws ParseException {
		List<StatisticsSalesmanIncomeDto> incomes = statisticsService.getSalesmanIncomeByPeriod(dateConverter(from), dateConverter(to));
		if (incomes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(incomes);
	}

	@GetMapping("/avg-income")
	public ResponseEntity<?> getAvgIncomesByPeriod(@RequestParam String from, @RequestParam String to) throws ParseException {
		StatisticsAvgIncomeDto incomes = statisticsService.getAvgIncomeByPeriod(dateConverter(from), dateConverter(to));
		if (isNull(incomes)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(incomes);
	}

	@GetMapping("/salesmen-avg-income")
	public ResponseEntity<?> getSalesmanAvgIncomesByPeriod(
			@RequestParam String from,
			@RequestParam String to) throws ParseException {
		List<StatisticsSalesmanAvgIncomeDto> incomes = statisticsService.getSalesmanAvgIncomeByPeriod(dateConverter(from), dateConverter(to));
		if (incomes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(incomes);
	}

	@GetMapping("/checks-num")
	public ResponseEntity<?> getChecksNumByPeriod(@RequestParam String from, @RequestParam String to) throws ParseException {
		StatisticsChecksDto checksNum = statisticsService.getChecksNumByPeriod(dateConverter(from), dateConverter(to));
		if (isNull(checksNum)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(checksNum);
	}

	@GetMapping("/salesmen-checks-num")
	public ResponseEntity<?> getSalesmanChecksNumByPeriod(
			@RequestParam String from,
			@RequestParam String to) throws ParseException {
		List<StatisticsSalesmanChecksDto> checksNum = statisticsService.getSalesmanChecksNumByPeriod(dateConverter(from), dateConverter(to));
		if (checksNum.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(checksNum);
	}

	@GetMapping("/proceeds")
	public ResponseEntity<?> getProceedsByPeriod(@RequestParam String from, @RequestParam String to) throws ParseException {
		StatisticsProceedsDto proceeds = statisticsService.getProceedsPeriod(dateConverter(from), dateConverter(to));
		if (isNull(proceeds)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(proceeds);
	}

	@GetMapping("/salesmen-proceeds")
	public ResponseEntity<?> getSalesmanProceedsByPeriod(
			@RequestParam String from,
			@RequestParam String to) throws ParseException {
		List<StatisticsSalesmanProceedsDto> proceeds = statisticsService.getSalesmanProceedsByPeriod(dateConverter(from), dateConverter(to));
		if (proceeds.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(proceeds);
	}

	private Timestamp dateConverter(String dateToConvert) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = dateFormat.parse(dateToConvert);
		return new java.sql.Timestamp(parsedDate.getTime());
	}


}
