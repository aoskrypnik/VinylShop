package com.vinyl.controller;

import com.vinyl.model.Record;
import com.vinyl.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;

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

	@GetMapping
	public List<Record> getAll() {
		return recordService.getAll();
	}

	@GetMapping("/{barcode}")
	public ResponseEntity<?> getRecordByBarcode(@PathVariable String barcode) {
		Record foundRecord = recordService.getByBarcode(barcode);
		if (isNull(foundRecord)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundRecord);
	}

	@PutMapping("/{barcode}")
	public ResponseEntity<?> updateRecord(@RequestBody Record record, @PathVariable String barcode) {
		if (isNull(recordService.getByBarcode(barcode))) {
			return ResponseEntity.notFound().build();
		}
		recordService.update(record);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getRecordByCriteria(@RequestParam(value = "wheres", required = false) List<String> whereParams,
												 @RequestParam(value = "likes", required = false) List<String> likeParams,
												 @RequestParam(value = "betweens", required = false) List<String> betweenParams,
												 @RequestParam(value = "joins", required = false) List<String> joins,
												 @RequestParam(value = "sort", required = false) String sorting,
												 @RequestParam(value = "order", required = false) String order) {
		List<Record> records = recordService.searchRecords(whereParams, likeParams, betweenParams, joins, sorting, order);
		if (records.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(records);
	}

}
