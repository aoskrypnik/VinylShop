package com.vinyl.controller;

import com.vinyl.dto.ArtistBandDto;
import com.vinyl.service.ArtistBandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/participation")
public class ArtistBandController {

	@Resource
	private ArtistBandService artistBandService;

	@GetMapping
	public List<ArtistBandDto> getAllParticipants() {
		return artistBandService.getAll();
	}

	@PostMapping
	public ResponseEntity<?> saveParticipation(@RequestBody ArtistBandDto artistBandDto) {
		artistBandService.save(artistBandDto);

		return ResponseEntity.status(201).build();
	}

	@PutMapping
	public ResponseEntity<?> updateParticipation(@RequestBody ArtistBandDto artistBandDto) {
		artistBandService.update(artistBandDto);
		return ResponseEntity.status(202).build();
	}

}
