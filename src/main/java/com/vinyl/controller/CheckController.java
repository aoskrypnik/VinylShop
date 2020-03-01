package com.vinyl.controller;

import com.vinyl.dto.CheckDto;
import com.vinyl.dto.SearchDto;
import com.vinyl.model.Check;
import com.vinyl.service.CheckService;
import com.vinyl.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/check")
public class CheckController {

	@Resource
	private CheckService checkService;
	@Resource
	private UserService userService;

	@GetMapping("/{checkNum}")
	public ResponseEntity<?> getCheckByCheckNum(@PathVariable Integer checkNum) {
		Check foundCheck = checkService.getByNum(checkNum);
		if (isNull(foundCheck)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundCheck);
	}

	@PostMapping
	public ResponseEntity<?> saveCheck(@RequestBody CheckDto checkDto) {
		Integer salesmanTabNum = userService.findSalesmanTabNumByLogin(userService.getCurrentlyLoggedInUserLogin());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Check check = new Check(timestamp, salesmanTabNum, checkDto.getCustomerNum(), checkDto.getProductBarcodes());
		Integer checkNum = checkService.save(check);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{checkNum}")
				.buildAndExpand(checkNum).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getCheckByCriteria(SearchDto searchDto) {
		List<Check> checks = checkService.searchChecks(searchDto);
		if (checks.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(checks);
	}

}
