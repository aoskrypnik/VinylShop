package com.vinyl.controller;

import com.vinyl.dto.CheckDto;
import com.vinyl.model.Check;
import com.vinyl.service.CheckService;
import com.vinyl.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.sql.Timestamp;

@RestController
public class CheckController {

	@Resource
	private CheckService checkService;
	@Resource
	private UserService userService;

	@PostMapping
	public ResponseEntity<?> saveCheck(@RequestBody CheckDto checkDto) {
		Integer salesmanTabNum = userService.findSalesmanTabNumByLogin(userService.getCurrentUserLogin());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Check check = new Check(timestamp, salesmanTabNum, checkDto.getCustomerNum(), checkDto.getProductBarcodes());
		Integer checkNum = checkService.save(check);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{checkNum}")
				.buildAndExpand(checkNum).toUri();
		return ResponseEntity.created(location).build();
	}

}
