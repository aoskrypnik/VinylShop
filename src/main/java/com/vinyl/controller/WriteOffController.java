package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.WriteOff;
import com.vinyl.service.WriteOffService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/write-off")
public class WriteOffController {

	@Resource
	private WriteOffService writeOffService;

	@GetMapping("/{writeOffNum}")
	public ResponseEntity<?> getWriteOffByNum(@PathVariable Integer writeOffNum) {
		WriteOff foundWriteOff = writeOffService.getByNum(writeOffNum);
		if (isNull(foundWriteOff)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundWriteOff);
	}

	@PostMapping
	public ResponseEntity<?> saveWriteOff(@RequestBody WriteOff writeOff) {
		Integer writeOffNum = null;
		try {
			writeOffNum = writeOffService.save(writeOff);
		} catch (AlbumAlreadyExistException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(406).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{writeOffNum}")
				.buildAndExpand(writeOffNum).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{writeOffNum}")
	public ResponseEntity<?> updateWriteOff(@RequestBody WriteOff writeOff, @PathVariable Integer writeOffNum) {
		if (isNull(writeOffService.getByNum(writeOffNum))) {
			return ResponseEntity.notFound().build();
		}
		writeOffService.update(writeOff, writeOffNum);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getWriteOffByCriteria(SearchDto searchDto) {
		List<WriteOff> writeOffList = writeOffService.searchWriteOffs(searchDto);
		if (writeOffList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(writeOffList);
	}
}
