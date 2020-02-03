package com.vinyl.controller;

import com.vinyl.dto.TrackPerformerDto;
import com.vinyl.service.TrackPerformerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/track/performer")
public class TrackPerformerController {

	@Resource
	private TrackPerformerService trackPerformerService;

	@PostMapping
	public ResponseEntity<?> saveAlbum(@RequestBody TrackPerformerDto trackPerformerDto) {
		trackPerformerService.save(trackPerformerDto);
		return ResponseEntity.ok().build();
	}

}
