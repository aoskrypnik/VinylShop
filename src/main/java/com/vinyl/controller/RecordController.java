package com.vinyl.controller;

import com.vinyl.model.Record;
import com.vinyl.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/record")
public class RecordController {

	@Resource
	private RecordService recordService;

	@PostMapping
	public ResponseEntity<?> saveRecord(@RequestBody Record record) {
		String generatedBarcode = recordService.save(record);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{barcode}")
				.buildAndExpand(generatedBarcode).toUri();
		return ResponseEntity.created(location).build();
	}

}
